package com.suyunpan.filesystem;

import com.suyunpan.mapper.FileInfoMapper;

import java.io.File;

public class CheckSize {
    public static long dirSize = 0;
    public static String MD5;

    public static long calDirSize(File[] files,FileInfoMapper fileInfoMapper){

        for (File file : files) {
            if(file.isFile()){
                MD5 = ReadMd5.readmd5(file);
                dirSize += Long.valueOf(fileInfoMapper.selectByPrimaryKey(MD5).getSize());
            }
        }

        return dirSize;
    }

}
