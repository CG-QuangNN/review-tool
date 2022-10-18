package basiclearning.version1;

import basiclearning.constant.CommentConst;
import basiclearning.util.ReadFileUtil;
import basiclearning.util.WriteFileUtil;

import java.io.IOException;
import java.util.List;

public class Main_readFile {
    public static void main(String[] args) throws IOException {
        StringBuilder totalMessage = new StringBuilder();

        List<String> lines = ReadFileUtil.readFile("src/main/java/basiclearning/data/input.csv");

        String[] names = lines.get(0).split(",");


        for (int i = 1; i < lines.size(); i++) {
            String[] links = lines.get(i).split(",");
            for (int j = 0; j < names.length; j++) {
                totalMessage.append(String.format("\n\n\n=========== %s ===========\n", names[j]));
                System.out.printf("\n\n\n=========== %d. %s ===========\n", j + 1, names[j]);
                if ("".equals(links[j])) {
                    totalMessage.append(CommentConst.CHUA_LAM_BAI_TAP);
                } else {
                    totalMessage.append(GitHubAPI_GetTree_Example.commentFileJava(links[j]));
                }
            }
//            totalMessage.append("\n");
        }

        WriteFileUtil.writeFile("src/main/java/basiclearning/data/ouput.csv", totalMessage.toString());
    }
}
