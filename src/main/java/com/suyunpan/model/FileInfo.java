package com.suyunpan.model;

public class FileInfo {
    private String md5;

    private String path;

    private String mttime;

    private String size;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMttime() {
        return mttime;
    }

    public void setMttime(String mttime) {
        this.mttime = mttime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}