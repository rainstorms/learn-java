package rain.file;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class BigFileReader {
    private int threadPoolSize;
    private Charset charset;
    private int bufferSize;
    private IFileHandle handle;
    private ExecutorService executorService;
    private long fileLength;
    private RandomAccessFile readFile;
    private RandomAccessFile writeFile;
    private Set<StartEndPair> startEndPairs;
    private CyclicBarrier cyclicBarrier;
    private AtomicLong counter = new AtomicLong(0);

    public static class Builder {
        private int threadSize = 1;
        private Charset charset;
        private int bufferSize = 1024 * 1024;
        private IFileHandle handle;
        private File srcFile;
        private File destFile;

        public Builder(String srcFile, String destFile, IFileHandle handle) {
            this.srcFile = new File(srcFile);
            if (!this.srcFile.exists())
                throw new IllegalArgumentException("文件不存在！");

            this.destFile = new File(destFile);
            if (!this.destFile.exists())
                throw new IllegalArgumentException("文件不存在！");

            this.handle = handle;
        }

        public Builder threadPoolSize(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("线程池参数必须为大于0的整数");
            }
            this.threadSize = size;
            return this;
        }

        public Builder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public Builder bufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public BigFileReader build() {
            return new BigFileReader(this.srcFile, this.destFile, this.handle, this.charset, this.bufferSize, this.threadSize);
        }
    }

    public BigFileReader(File srcFile, File destFile, IFileHandle handle, Charset charset, int bufferSize, int threadPoolSize) {
        this.fileLength = srcFile.length();
        this.handle = handle;
        this.charset = charset;
        this.bufferSize = bufferSize;
        this.threadPoolSize = threadPoolSize;
        try {
            this.readFile = new RandomAccessFile(srcFile, "r");
            this.writeFile = new RandomAccessFile(destFile, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        startEndPairs = new HashSet<>();
    }

    public void start() {
        final long startTime = System.currentTimeMillis();
        long everySize = this.fileLength / this.threadPoolSize;
        try {
            calculateStartEnd(0, everySize);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        cyclicBarrier = new CyclicBarrier(startEndPairs.size(), () -> {
            System.out.println("all line: " + counter.get());
            shutdown();
        });
        for (StartEndPair pair : startEndPairs) {
            System.out.println("分配分片：" + pair);
            this.executorService.execute(new SliceReaderTask(pair));
        }

        System.out.println("use time: " + (System.currentTimeMillis() - startTime));
    }

    private void calculateStartEnd(long start, long size) throws IOException {
        if (start > fileLength - 1) return;

        StartEndPair pair = new StartEndPair();
        pair.start = start;
        long endPosition = start + size - 1;
        if (endPosition >= fileLength - 1) {
            pair.end = fileLength - 1;
            startEndPairs.add(pair);
            return;
        }

        readFile.seek(endPosition);
        byte tmp = (byte) readFile.read();
        while (tmp != '\n' && tmp != '\r') {
            endPosition++;
            if (endPosition >= fileLength - 1) {
                endPosition = fileLength - 1;
                break;
            }
            readFile.seek(endPosition);
            tmp = (byte) readFile.read();
        }
        pair.end = endPosition;
        startEndPairs.add(pair);

        calculateStartEnd(endPosition + 1, size);
    }

    public void shutdown() {
        try {
            this.readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.executorService.shutdown();
    }

    private void handle(byte[] bytes) {
        String line;
        if (this.charset == null) {
            line = new String(bytes);
        } else {
            line = new String(bytes, charset);
        }
        if (!"".equals(line)) {
            this.handle.handle(line);
            counter.incrementAndGet();
        }
    }


    private static class StartEndPair {
        public long start;
        public long end;

        @Override
        public String toString() {
            return "star=" + start + ";end=" + end;
        }
    }

    private class SliceReaderTask implements Runnable {
        private long start;
        private long sliceSize;
        private byte[] readBuff;

        public SliceReaderTask(StartEndPair pair) {
            this.start = pair.start;
            this.sliceSize = pair.end - pair.start + 1;
            this.readBuff = new byte[bufferSize];
        }


        @Override
        public void run() {
            try {
                MappedByteBuffer mapBufferi = readFile.getChannel().map(FileChannel.MapMode.READ_ONLY, start, this.sliceSize);
                MappedByteBuffer mapBuffero = writeFile.getChannel().map(FileChannel.MapMode.READ_WRITE, start, this.sliceSize);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                for (int offset = 0; offset < sliceSize; offset += bufferSize) {
                    int readLength;
                    if (offset + bufferSize <= sliceSize) {
                        readLength = bufferSize;
                    } else {
                        readLength = (int) (sliceSize - offset);
                    }
                    mapBufferi.get(readBuff, 0, readLength);

                    byte[] temp = new byte[0];
                    for (int i = 0; i < readLength; i++) {
                        byte tmp = readBuff[i];
                        //碰到换行符
                        if (tmp == '\n' || tmp == '\r') {
//                            handle(bos.toByteArray());
                            temp = bos.toByteArray();
                            String s = new String(temp, charset);
                            bos.reset();
                        } else {
                            bos.write(tmp);
                        }
                    }

                    mapBuffero.put(temp);
                }
                if (bos.size() > 0) {
                    handle(bos.toByteArray());
                }
                cyclicBarrier.await();//测试性能用
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
