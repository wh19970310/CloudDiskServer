package com.suyunpan.mapper;

import com.suyunpan.model.ShareFile;

import java.util.List;

public interface ShareFileMapper {
    int deleteByPrimaryKey(String sharelink);

    int insert(ShareFile record);

    int insertSelective(ShareFile record);

    ShareFile selectByPrimaryKey(String sharelink);

    int updateByPrimaryKeySelective(ShareFile record);

    int updateByPrimaryKey(ShareFile record);

    List<ShareFile> selectByUserId(Integer userId);
}