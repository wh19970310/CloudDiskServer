package com.suyunpan.model;

public class UserSpaceFile {
    private String fileName;
    private String fileSize = "-1";
    private String fileOperation;

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    private String fileMD5;

    public String toString() {

        return fileName + "---" + fileSize + "---" + fileOperation;
    }

    public String getFileOperation() {
        return fileOperation;
    }

    public void setFileOperation(String fileOperation) {
        this.fileOperation = fileOperation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }


}
