package rain.lombda;

import java.io.*;

public class IOHideTryCatch {
    // 1、先定义函数式接口
    // 意味着 可以将行为（即方法体）变成参数
    @FunctionalInterface
    public interface InputStreamInterface {
        String process(BufferedReader reader) throws IOException;
    }

    // 2、将想隐藏的共有代码放在新方法中，通过函数接口去调用
    public static String file2BufferedReader(InputStreamInterface inputStreamInterface, File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            return inputStreamInterface.process(br);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        File file = new File("");
        // 3、调用新的方法，
        // 第一个参数是方法体
        // 第二个参数是file
        file2BufferedReader(reader -> {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }, file);

        // 原来的方法和用法
        old(file);
    }

    //
    public static String old(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
