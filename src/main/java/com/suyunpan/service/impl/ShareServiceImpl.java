package com.suyunpan.service.impl;

import com.alibaba.fastjson.JSON;
import com.suyunpan.mapper.ShareFileMapper;
import com.suyunpan.model.ShareFile;
import com.suyunpan.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private ShareFileMapper shareFileMapper;

    @Override
    public String getShareList(String userId) {

        List<ShareFile> list = shareFileMapper.selectByUserId(Integer.valueOf(userId));
        return JSON.toJSONString(list);

    }

    @Override
    public String deleteShareLink(String shareLink) {
        int isDel = shareFileMapper.deleteByPrimaryKey(shareLink);
        if(isDel == 1)
        {
            return "deleteShareLink";
        }else {
            return "deleteError";
        }
    }
}
