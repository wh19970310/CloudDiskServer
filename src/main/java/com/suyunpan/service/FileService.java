package com.suyunpan.service;

import com.suyunpan.model.FileInfo;

import java.io.IOException;

public interface FileService {
    FileInfo queryFileInfoByMd5(String md5);

    String createShortcut(String MD5, String userName, String fileName,
                          String fileSize, boolean isSecondPass) throws IOException;

    int insertFileInfo(FileInfo fileInfo, String userName, String fileName) throws IOException;

    boolean copyFile(String userName, String resourcePath, String targetPath, boolean isFile);

    boolean deleteFile(String userName, String resourcePath, String uuid);

    boolean createDirectory(String resourcePath);

    boolean renameFile(String oldFileName, String newFileName);

    String shareFile(String fileMD5, Integer userId, String fileName);
}
