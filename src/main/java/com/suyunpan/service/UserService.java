package com.suyunpan.service;

import com.suyunpan.model.User;

import java.io.IOException;

public interface UserService {
    User queryUserByUserId(Integer userid, String passWord);

    boolean getCode(String phoneNum, String forCode);

    User queryUserByPhoneNum(String phoneNum);

    User signUpByPhoneNum(String phoneNum, String passWord, String checkCode);

    boolean checkSpace(String userName, String fileSize);

    User selectUserByUserId(Integer userId);

    boolean updateUserInfo(User user, String checkCode, String phoneNum);

    String getShareFile(String userId, String shareLink, String shareCode) throws IOException;

    User retrieve(String phoneNum, String code);
}
