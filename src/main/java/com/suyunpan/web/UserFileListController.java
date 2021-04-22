package com.suyunpan.web;

import com.suyunpan.filesystem.ReadMd5;
import com.suyunpan.model.UserSpaceFile;
import com.suyunpan.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserFileListController {
    @Autowired
    private FileService fileService;

    @RequestMapping("/filelist")
    public List traversalUserFile(String userPath) {
        String path = "D:/UserSpace/" + userPath;
        List<UserSpaceFile> userSpaceFileList = new ArrayList<>();
        String MD5 = "";
        File file = new File(path);        //获取其file对象
        File[] listFiles = file.listFiles();    //遍历path下的文件和目录，放在File数组中

        for (File f : listFiles) {                    //遍历File[]数组
            UserSpaceFile userSpaceFile = new UserSpaceFile();
            if (f.isFile()) {
                userSpaceFile.setFileName(f.getName());
                userSpaceFile.setFileOperation("下载");
                MD5 = ReadMd5.readmd5(f);
                userSpaceFile.setFileMD5(MD5);
                userSpaceFile.setFileSize(fileService.queryFileInfoByMd5(MD5).getSize());
            } else {
                userSpaceFile.setFileName(f.getName());
                userSpaceFile.setFileOperation("打开");
            }
            userSpaceFileList.add(userSpaceFile);
        }
        return userSpaceFileList;
    }
}
