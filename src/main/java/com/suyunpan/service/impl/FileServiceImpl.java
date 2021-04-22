package com.suyunpan.service.impl;

import com.suyunpan.filesystem.*;
import com.suyunpan.mapper.FileInfoMapper;
import com.suyunpan.mapper.ShareFileMapper;
import com.suyunpan.mapper.UserMapper;
import com.suyunpan.model.FileInfo;
import com.suyunpan.model.ShareFile;
import com.suyunpan.model.User;
import com.suyunpan.service.FileService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShareFileMapper shareFileMapper;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public FileInfo queryFileInfoByMd5(String md5) {
        return fileInfoMapper.selectByPrimaryKey(md5);
    }

    public String createShortcut(String MD5, String userName, String fileName,
                                 String fileSize, boolean isSecondPass) throws IOException {

        User user = userMapper.selectByPrimaryKey(Integer.valueOf(userName));
        user.setFreespace(String.valueOf(Long.valueOf(user.getFreespace())
                - Long.valueOf(fileSize)));
        userMapper.updateByPrimaryKeySelective(user);

        File file = new File("D:/UserSpace/" + userName + "/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            return "FileExists";
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(MD5); // \r\n即为换行
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
        return "secondpass";
    }

    @Override
    @Transactional
    public int insertFileInfo(FileInfo fileInfo, String userName, String fileName) throws IOException {

        int isInsert = fileInfoMapper.insert(fileInfo);
        createShortcut(fileInfo.getMd5(), userName, fileName, fileInfo.getSize(), true);
        return isInsert;
    }

    @Override
    public boolean copyFile(String userName, String filePath, String desPath, boolean isFile) {
        User user = userMapper.selectByPrimaryKey(Integer.valueOf(userName));
        String MD5;
        Long fileSize;
        CopyFile copyFile = new CopyFile();
        copyFile.setFile(isFile);
        if (isFile) {
            MD5 = ReadMd5.readmd5(new File(filePath));
            fileSize = Long.valueOf(fileInfoMapper.selectByPrimaryKey(MD5).getSize());
            copyFile.setResource(new File(filePath));
            copyFile.setTarget(new File(desPath));
        } else {
            File[] files = new File(filePath).listFiles();
            fileSize = CheckSize.calDirSize(files, fileInfoMapper);
            copyFile.setResourcePath(filePath);
            copyFile.setTargetPath(desPath);
        }
        user.setFreespace(String.valueOf(Long.valueOf(user.getFreespace()) - fileSize));
        userMapper.updateByPrimaryKeySelective(user);
        return copyFile.fileOperation();
    }

    @Override
    public boolean deleteFile(String userName, String resourcePath, String uuid) {
        String MD5;
        long fileSize;
        User user = userMapper.selectByPrimaryKey(Integer.valueOf(userName));

        if (redisTemplate.opsForValue().get(Integer.valueOf(userName)).equals(uuid)) {
            File file = new File(resourcePath);
            if (file.isFile()) {
                MD5 = ReadMd5.readmd5(new File(resourcePath));
                fileSize = Long.valueOf(fileInfoMapper.selectByPrimaryKey(MD5).getSize());
            } else {
                fileSize = CheckSize.calDirSize(file.listFiles(), fileInfoMapper);
            }
            user.setFreespace(String.valueOf(Long.valueOf(user.getFreespace()) + fileSize));
            userMapper.updateByPrimaryKeySelective(user);
            DeleteFile deleteFile = new DeleteFile();
            return deleteFile.fileOperation(new File(resourcePath));
        } else {
            return false;
        }

    }

    @Override
    public boolean createDirectory(String resourcePath) {
        CreateDirectory createDirectory = new CreateDirectory();
        return createDirectory.createDirectory(resourcePath);
    }

    @Override
    public boolean renameFile(String oldFileName, String newFileName) {
        return new RenameFile().renameFile(oldFileName, newFileName);
    }

    @Override
    public String shareFile(String fileMD5, Integer userId, String fileName) {

        String shareLink = String.valueOf(userId) + UUID.randomUUID() + fileMD5;
        String shareCode = RandomStringUtils.randomAlphanumeric(4);

        ShareFile shareFile = new ShareFile();
        shareFile.setSharelink(shareLink);
        shareFile.setMd5(fileMD5);
        shareFile.setBelonguser(userId);
        shareFile.setSharecode(shareCode);
        shareFile.setFilename(fileName);

        if (shareFileMapper.insertSelective(shareFile) == 1) {
            return shareLink + "  分享码： " + shareCode;
        } else {
            return "分享出错！";
        }
    }
}
