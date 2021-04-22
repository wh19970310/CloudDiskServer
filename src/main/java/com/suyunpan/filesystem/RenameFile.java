package com.suyunpan.filesystem;

import java.io.File;

public class RenameFile {
    public boolean renameFile(String oldName,String newName){
        File file = new File(oldName);
        return file.renameTo(new File(newName));
    }
}
