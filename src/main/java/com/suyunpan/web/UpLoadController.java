package com.suyunpan.web;

import com.suyunpan.appdata.AppData;
import com.suyunpan.model.FileInfo;
import com.suyunpan.service.FileService;
import com.suyunpan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class UpLoadController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @RequestMapping("/readyupload")
    public String upLoad(String MD5, String fileSize, String userId, String fileName) throws IOException {
        boolean isCanUp;
        isCanUp = userService.checkSpace(userId,fileSize);

        if(isCanUp){
            FileInfo fileInfo = fileService.queryFileInfoByMd5(MD5);
            if (fileInfo == null) {
                fileInfo = new FileInfo();
                fileInfo.setMd5(MD5);
                fileInfo.setSize(fileSize);
                fileInfo.setPath("D:/FileStore/" + MD5);
                fileInfo.setMttime(df.format(new Date()));
                AppData.fileInfoMap.put(MD5, fileInfo);
                return "readyUpLoad";
            } else {
                String isSecondpass = fileService.createShortcut(MD5, userId, fileName,fileSize,true);
                if (isSecondpass.equals("secondpass")) {
                    return "secondpass";
                } else {
                    return "FileExists";
                }
            }
        }else {
            return "SpaceNotEnough";
        }


    }

    @RequestMapping("/endupload")
    public String endUpLoad(String MD5, String userId, String fileName) throws IOException {
        int insert = 0;
        if (AppData.fileInfoMap.containsKey(MD5)) {
            insert = fileService.insertFileInfo(AppData.fileInfoMap.get(MD5), userId, fileName);
            if (insert > 0) {
                AppData.fileInfoMap.remove(MD5);
            }
        }
        return String.valueOf(insert);
    }
}
