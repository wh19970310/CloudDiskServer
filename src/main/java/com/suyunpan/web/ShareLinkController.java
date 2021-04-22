package com.suyunpan.web;


import com.suyunpan.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShareLinkController {

    @Autowired
    private ShareService shareService;

    @RequestMapping("/getShareList")
    public String getShareList(String userId){


        return shareService.getShareList(userId);
    }

    @RequestMapping("/deleteShareLink")
    public String deleteShareLink(String shareLink){

        return shareService.deleteShareLink(shareLink);
    }

}
