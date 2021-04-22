package com.suyunpan.filesystem;

import java.io.File;

public class CreateDirectory {
    public boolean createDirectory(String resourcePath){
        int i = 1;
        boolean isCreate;
        File file = new File(resourcePath + "/新建文件夹");
        while(true){
            isCreate = file.mkdirs();
            if(!isCreate){
                file = new File(resourcePath + "/新建文件夹（" + ++i + "）");
            }else {
                break;
            }
        }
        return isCreate;
    }
}
