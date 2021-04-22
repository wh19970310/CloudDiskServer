package com.suyunpan.filesystem;

import java.io.File;

public class CreateUserSpace {
    public boolean createDir(String path) {
        File file = new File(path);
        return file.mkdirs();
    }
}
