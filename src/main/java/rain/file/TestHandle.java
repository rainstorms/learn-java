package rain.file;

import com.google.common.base.Splitter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TestHandle implements IFileHandle {
    private File wxFile;
    private File itvFile;
    private File errorFile;
    private long successNum = 0;
    private long failNum = 0;
    private String groupId;

    @Override public void handle(String line) {
        try (FileOutputStream outWxFile = new FileOutputStream(wxFile);
             FileOutputStream outErrorFile = new FileOutputStream(errorFile);
             FileOutputStream outItvFile = new FileOutputStream(itvFile)) {

            List<String> split = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(line);
            if (split.size() != 2) {
                outErrorFile.write(line.getBytes());
                failNum += 1;
                return;
            }

            String prefix = split.get(0);
            StringBuilder account = new StringBuilder();
            account.append(split.get(1));
            if (StringUtils.equalsAny(prefix, "f", "F")) {
                successNum += 1;
                account.append("$").append(groupId).append("\n");
                outItvFile.write(account.toString().getBytes());
            } else if (StringUtils.equalsAny(prefix, "W", "w")) {
                successNum += 1;
                account.append("$").append(groupId).append("\n");
                outWxFile.write(account.toString().getBytes());
            } else {
                failNum += 1;
                outErrorFile.write(line.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("failNum:" + failNum);
            System.out.println("successNum:" + successNum);
        }
    }
}
