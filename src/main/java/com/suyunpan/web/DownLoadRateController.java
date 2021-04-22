package com.suyunpan.web;

import com.suyunpan.appdata.AppData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownLoadRateController {
    @RequestMapping("/downloadrate")
    public String getDownLoadRate(String fileMD5) {
        String respone = AppData.downLoadRateMap.get(fileMD5);
        if ("100%".equals(respone) || "pause".equals(respone)) {
            AppData.downLoadRateMap.remove(fileMD5);
        }

        return respone;
    }
}
