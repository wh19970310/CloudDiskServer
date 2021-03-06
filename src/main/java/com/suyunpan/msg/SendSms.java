package com.suyunpan.msg;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SendSms {

    private DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "yourAliCode", "yourAliCode");
    private IAcsClient client = new DefaultAcsClient(profile);
    private CommonRequest request = new CommonRequest();
    private String phoneNum;
    private Integer random;

    public Integer sendSms() {

        random = (int) (((Math.random() * 9) + 1) * 100000);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("yourAliCode");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "YourProjectName");
        request.putQueryParameter("TemplateCode", "YourTemplete");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + random + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
        } catch (ServerException e) {
            return -1;
        } catch (ClientException e) {
            return -1;
        }
        return random;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}
