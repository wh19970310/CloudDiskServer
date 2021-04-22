package com.suyunpan.service.impl;

import com.suyunpan.filesystem.CreateUserSpace;
import com.suyunpan.mapper.ShareFileMapper;
import com.suyunpan.mapper.UserMapper;
import com.suyunpan.model.FileInfo;
import com.suyunpan.model.ShareFile;
import com.suyunpan.model.User;
import com.suyunpan.msg.GetCode;
import com.suyunpan.service.FileService;
import com.suyunpan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShareFileMapper shareFileMapper;
    @Autowired
    private FileService fileService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public User queryUserByUserId(Integer userid, String passWord) {

        User user = userMapper.selectByPrimaryKey(userid);

        if (user != null
                && !(passWord == null || passWord.length() == 0)
                && !(user.getPwd() == null || user.getPwd().length() == 0)
                && passWord.equals(user.getPwd())) {

            user.setUUID(String.valueOf(UUID.randomUUID()));
            redisTemplate.opsForValue().set(user.getUserid(), user.getUUID(), 3, TimeUnit.DAYS);

        } else {
            user = null;
        }
        return user;
    }

    @Override
    public boolean getCode(String phoneNum, String forCode) {
        Integer random = -1;
        if (forCode.equals("signUp") && (userMapper.selectByPhoneNum(phoneNum) == null)) { // for sign up
            User user = userMapper.selectByPhoneNum(phoneNum);
            GetCode getCode = new GetCode();
            return getCode.getForSignUp(user, redisTemplate, phoneNum, random);

        } else if (forCode.equals("updateInfo")) { // for update

            GetCode getCode = new GetCode();
            return getCode.forCode(phoneNum, random, redisTemplate);

        } else if (forCode.equals("Retrieve")) {
            if(userMapper.selectByPhoneNum(phoneNum) != null){
                GetCode getCode = new GetCode();
                return getCode.forCode(phoneNum, random, redisTemplate);
            }else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public User queryUserByPhoneNum(String phoneNum) {

        return userMapper.selectByPhoneNum(phoneNum);
    }

    @Override
    public User signUpByPhoneNum(String phoneNum, String passWord, String checkCode) {

        if (redisTemplate.opsForValue().get(phoneNum).equals(Integer.valueOf(checkCode))) {
            User user = new User();
            user.setPhonenum(phoneNum);
            user.setPwd(passWord);
            int res = userMapper.insertSelective(user);
            CreateUserSpace createUserSpace = new CreateUserSpace();
            if (res == 1) {
                user = userMapper.selectByPhoneNum(phoneNum);
                createUserSpace.createDir("D:/UserSpace/" + user.getUserid());
                return user;
            }
            redisTemplate.delete(phoneNum);
        }
        return null;
    }

    @Override
    public boolean checkSpace(String userName, String fileSize) {
        User user = userMapper.selectByPrimaryKey(Integer.valueOf(userName));
        if (Long.valueOf(user.getFreespace()) >= Long.valueOf(fileSize)) {
            return true;
        }
        return false;
    }

    @Override
    public User selectUserByUserId(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean updateUserInfo(User user, String checkCode, String phoneNum) {
        if ((checkCode == null) && (phoneNum == null)) { // 实际上都为空，仅修改昵称
            if (userMapper.updateByPrimaryKeySelective(user) == 1) {
                return true;
            }
        } else if (Integer.valueOf(checkCode).equals(redisTemplate.opsForValue().get(phoneNum))) {
            redisTemplate.delete(phoneNum);
            if ((phoneNum.equals(user.getPhonenum())
                    || (userMapper.selectByPhoneNum(user.getPhonenum()) == null))
                    && (userMapper.updateByPrimaryKeySelective(user) == 1)) {

                return true;
            }
        }

        return false;
    }

    @Override
    public String getShareFile(String userId, String shareLink, String shareCode) throws IOException {
        ShareFile shareFile = shareFileMapper.selectByPrimaryKey(shareLink);
        if (shareFile == null) {
            return "分享链接不正确！";
        } else if (!shareFile.getSharecode().equals(shareCode)) {
            return "分享码不正确！";
        } else {
            FileInfo fileInfo = fileService.queryFileInfoByMd5(shareFile.getMd5());
            fileService.createShortcut(shareFile.getMd5(), userId, shareFile.getFilename(), fileInfo.getSize(), false);
            return "文件已添加！";
        }
    }

    @Override
    public User retrieve(String phoneNum, String code) {
        if (Integer.valueOf(code).equals(redisTemplate.opsForValue().get(phoneNum))) {
            return userMapper.selectByPhoneNum(phoneNum);
        } else {
            return null;
        }

    }
}
