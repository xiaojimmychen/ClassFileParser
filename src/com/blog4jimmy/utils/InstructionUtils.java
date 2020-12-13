package com.blog4jimmy.utils;

import java.io.*;
import java.util.HashMap;

public class InstructionUtils {
    public static HashMap<Integer, String> sInstructionMap = null;

    public static String getInstruction(int byteCode) {
        if (sInstructionMap == null) {
            initInstruction();
        }
        return sInstructionMap.get(byteCode);
    }

    // 从instruction.txt文件中读取指令码十六进制和字符串的对应关系放置到map中
    public static void initInstruction() {
        sInstructionMap = new HashMap<>();
        try {
            File file = new File("instruction.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            int i = 0;
            String line;
            Integer ins = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (i % 2 == 0) {
                    ins = Integer.parseInt(line.substring(2, 4), 16);
                } else if (i % 2 == 1) {
                    sInstructionMap.put(ins, line);
                }
                i++;
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
