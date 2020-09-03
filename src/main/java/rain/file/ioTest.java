package rain.file;

public class ioTest {

    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);
        return;

//        String groupId = "11";
//        String dirTemp = "/Users/apple/dd";
//        File originalFile = new File(dirTemp + "/1.txt");
//
//        String wxPath = dirTemp + "/wx.txt";
//        File wxFile = new File(wxPath);
//
//        String itvPath = dirTemp + "/itv.txt";
//        File itvFile = new File(itvPath);
//
//        String errorPath = dirTemp + "/error.txt";
//        File errorFile = new File(errorPath);
//
//        long start = System.currentTimeMillis();
//        try (FileOutputStream outWxFile = new FileOutputStream(wxFile);
//             FileOutputStream out = new FileOutputStream(originalFile);
//             FileOutputStream outErrorFile = new FileOutputStream(errorFile);
//             FileOutputStream outItvFile = new FileOutputStream(itvFile)) {
//
////            out.write(file.getBytes());
//
//            FileReader fileReader = new FileReader(originalFile);
//            BufferedReader br = new BufferedReader(fileReader);
//
//            long successNum = 0;
//            long failNum = 0;
//            String line;
//            while ((line = br.readLine()) != null) {//从文件中读入到内存中
//                line += "\n";
//                List<String> split = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(line);
//                if (split.size() != 2) {
//                    outErrorFile.write(line.getBytes());
//                    failNum += 1;
//                    continue;
//                }
//
//                String prefix = split.get(0);
//
//                StringBuilder account = new StringBuilder();
//                account.append(split.get(1));
//                if (StringUtils.equalsAny(prefix, "f", "F")) {
//                    successNum += 1;
//                    account.append("$").append(groupId).append("\n");
//                    outItvFile.write(account.toString().getBytes());
//                } else if (StringUtils.equalsAny(prefix, "W", "w")) {
//                    successNum += 1;
//                    account.append("$").append(groupId).append("\n");
//                    outWxFile.write(account.toString().getBytes());
//                } else {
//                    failNum += 1;
//                    outErrorFile.write(line.getBytes());
//                }
//            }
//
//            if (itvFile.length() > 0) {
//                outItvFile.write(("0$" + groupId).getBytes());
//            }
//
//            if (wxFile.length() > 0) {
//                outWxFile.write(("0$" + groupId).getBytes());
//            }
//
//            System.out.println("successNum:" + successNum);
//            System.out.println("failNum:" + failNum);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
    }
}
