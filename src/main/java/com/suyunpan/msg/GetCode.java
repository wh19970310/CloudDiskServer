package com.suyunpan.msg;

import com.suyunpan.model.User;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class GetCode {

    public boolean getForSignUp(User user, RedisTemplate<Object, Object> redisTemplate, String phoneNum, Integer random) {

        if (user == null) {
            SendSms sendSms = new SendSms();
            sendSms.setPhoneNum(phoneNum);
            random = sendSms.sendSms();
            if (random == -1) {
                return false;
            } else {
                redisTemplate.opsForValue().set(phoneNum, random, 6, TimeUnit.MINUTES);
                return true;
            }
        } else {

            return false;
        }
    }


    public boolean forCode(String phoneNum, Integer random, RedisTemplate<Object, Object> redisTemplate) {
        SendSms sendSms = new SendSms();
        sendSms.setPhoneNum(phoneNum);
        random = sendSms.sendSms();
        if (random != -1) {
            redisTemplate.opsForValue().set(phoneNum, random);
            return true;
        } else {
            return false;
        }

    }
}
