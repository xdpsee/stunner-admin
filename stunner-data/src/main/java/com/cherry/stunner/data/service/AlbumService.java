package com.cherry.stunner.data.service;

import com.cherry.stunner.data.enums.AlbumStatus;

public interface AlbumService {

    long createAlbum(String url, String originTitle, String originCoverUrl);

    Long existAlbumByUrl(String url);

    int updateAlbum(long albumId, String title, String coverUrl, int coverWidth, int coverHeight);

    int updateAlbumStatus(long albumId, AlbumStatus status);


}

