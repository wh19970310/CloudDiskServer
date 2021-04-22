package com.suyunpan.web;

import com.alibaba.fastjson.JSON;
import com.suyunpan.model.User;
import com.suyunpan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/userlogin")
    public User queryUserByUserId(String userId, String passWord) {

        return userService.queryUserByUserId(Integer.valueOf(userId), passWord);
    }

    @RequestMapping("/getcode")
    public boolean signUp(String phoneNum, String forCode) {

        return userService.getCode(phoneNum, forCode);
    }

    @RequestMapping("/signup")
    public User signUpByPhoneNum(String phoneNum, String passWord, String checkCode) {
        return userService.signUpByPhoneNum(phoneNum, passWord, checkCode);
    }

    @RequestMapping("/getuserinfo")
    public User selectUserByUserId(String userId) {
        return userService.selectUserByUserId(Integer.valueOf(userId));
    }

    @RequestMapping("/updateInfo")
    public boolean updateUserInfo(String userJson, String checkCode, String phoneNum) {
        User user = JSON.parseObject(userJson, User.class);
        return userService.updateUserInfo(user, checkCode, phoneNum);
    }

    @RequestMapping("/Retrieve")
    public User retrieve(String phoneNum, String code) {
        return userService.retrieve(phoneNum, code);
    }


}
