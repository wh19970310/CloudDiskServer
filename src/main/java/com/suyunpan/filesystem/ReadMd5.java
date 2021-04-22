package com.suyunpan.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadMd5 {

    public static String readmd5(File f) {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(f));
            String tempStr;
            tempStr = reader.readLine();
            sbf.append(tempStr);
            reader.close();
            return sbf.toString();
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
