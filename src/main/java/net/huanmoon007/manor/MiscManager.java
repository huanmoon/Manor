package net.huanmoon007.manor;

import java.io.*;

public class MiscManager {
    //thanks ChatGPT for its help =)
    public void ModifyFileLine(String FilePath, int LineNumber, String NewLineContent) {
        try {
            // 读取文件
            File file = new File(FilePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder content = new StringBuilder();
            int currentLineNumber = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLineNumber == LineNumber) {
                    // 如果是要修改的行，则替换为新的行内容
                    content.append(NewLineContent).append("\n");
                } else {
                    // 否则保留原来的行内容
                    content.append(line).append("\n");
                }
                currentLineNumber++;
            }
            reader.close();

            // 写入文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toString());
            writer.flush();

            System.out.println("Successfully modified line " + LineNumber + " in file " + FilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
