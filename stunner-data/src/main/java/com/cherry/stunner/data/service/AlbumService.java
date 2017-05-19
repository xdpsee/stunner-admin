package com.cherry.stunner.data.service;

import com.cherry.stunner.data.enums.AlbumStatus;

public interface AlbumService {

    long createAlbum(long categoryId, String originTitle, String originCoverUrl);

    int updateAlbum(long albumId, String title, String coverUrl, int coverWidth, int coverHeight);

    int updateAlbumStatus(long albumId, AlbumStatus status);

    int insertAlbumTag(long albumId, long tagId);


}

