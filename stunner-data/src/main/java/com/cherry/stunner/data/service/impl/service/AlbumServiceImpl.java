package com.cherry.stunner.data.service.impl.service;

import com.cherry.stunner.data.enums.AlbumStatus;
import com.cherry.stunner.data.po.Album;
import com.cherry.stunner.data.service.AlbumService;
import com.cherry.stunner.data.service.impl.mapper.AlbumMapper;
import com.cherry.stunner.data.service.impl.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public long createAlbum(String originUrl, String originTitle, String originCoverUrl) {

        Album album = new Album();
        album.setOriginUrl(originUrl);
        album.setOriginTitle(originTitle);
        album.setOriginCoverUrl(originCoverUrl);
        album.setTitle(originTitle);

        try {
            albumMapper.insertAlbum(album);
        } catch (Exception e) {
            if (!ExceptionUtils.hasDuplicateEntryException(e)) {
                throw e;
            }

            album = albumMapper.selectByOriginUrl(originUrl);
        }

        return album.getId();
    }

    @Override
    public Long existAlbumByUrl(String url) {
        return albumMapper.selectAlbumIdByOriginUrl(url);
    }

    @Override
    public int updateAlbum(long albumId, String title, String coverUrl, int coverWidth, int coverHeight) {
        return 0;
    }

    @Override
    public int updateAlbumStatus(long albumId, AlbumStatus status) {
        return 0;
    }

}
