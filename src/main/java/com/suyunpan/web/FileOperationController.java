package com.suyunpan.web;

import com.suyunpan.service.FileService;
import com.suyunpan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FileOperationController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @RequestMapping("/copyfile")
    public boolean copyFile(String userId, String resourcePath, String targetPath, String isFile) {

        resourcePath = "D:/UserSpace/" + userId + resourcePath;
        targetPath = "D:/UserSpace/" + userId + targetPath;
        return fileService.copyFile(userId, resourcePath, targetPath, Boolean.valueOf(isFile));
    }

    @RequestMapping("/deletefile")
    public boolean deleteFile(String userId, String resourcePath, String uuid) {

        resourcePath = "D:/UserSpace/" + userId + resourcePath;
        return fileService.deleteFile(userId, resourcePath, uuid);
    }

    @RequestMapping("/cutfile")
    public boolean cutFile(String userId, String resourcePath, String targetPath, String isFile, String uuid) {

        if (redisTemplate.opsForValue().get(Integer.valueOf(userId)).equals(uuid)) {
            boolean isCut = true;
            resourcePath = "D:/UserSpace/" + userId + resourcePath;
            targetPath = "D:/UserSpace/" + userId + targetPath;
            return isCut & fileService.copyFile(userId, resourcePath, targetPath, Boolean.valueOf(isFile))
                    & fileService.deleteFile(userId, resourcePath, uuid);
        } else {
            return false;
        }
    }

    @RequestMapping("/createdirectory")
    public boolean createDirectory(String userId, String resourcePath) {
        resourcePath = "D:/UserSpace/" + userId + resourcePath;
        return fileService.createDirectory(resourcePath);
    }

    @RequestMapping("/renamefile")
    public boolean renameFile(String userId, String oldFilePath, String newFilePath) {
        oldFilePath = "D:/UserSpace/" + userId + oldFilePath;
        newFilePath = "D:/UserSpace/" + userId + newFilePath;
        return fileService.renameFile(oldFilePath, newFilePath);
    }

    @RequestMapping("/shareFile")
    public String shareFile(String fileMD5, String userId, String fileName) {

        return fileService.shareFile(fileMD5, Integer.valueOf(userId), fileName);
    }

    @RequestMapping("/getShare")
    public String getShareFile(String userId, String shareLink, String shareCode) throws IOException {
        return userService.getShareFile(userId, shareLink, shareCode);
    }

}