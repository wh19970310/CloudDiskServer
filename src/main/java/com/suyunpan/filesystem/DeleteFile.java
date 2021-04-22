package com.suyunpan.filesystem;

import java.io.File;

public class DeleteFile {

    public boolean fileOperation(File resource) {
        if(resource.isFile()){
            return resource.delete();
        }else if(resource.isDirectory() && resource.listFiles().length == 0){
            return resource.delete();
        }else {
            boolean isDel = true;
            File[] files = resource.listFiles();
            for (File file: files) {
                isDel = isDel & fileOperation(file);
            }
            return isDel & fileOperation(resource);
        }
    }
}
