package com.suyunpan.service;

public interface ShareService {
    String getShareList(String userId);

    String deleteShareLink(String shareLink);
}
