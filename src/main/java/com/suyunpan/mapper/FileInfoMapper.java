package com.suyunpan.mapper;

import com.suyunpan.model.FileInfo;

public interface FileInfoMapper {
    int deleteByPrimaryKey(String md5);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(String md5);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);
}