package com.cherry.stunner.data.service.impl.service;

import com.cherry.stunner.data.enums.AlbumStatus;
import com.cherry.stunner.data.po.Album;
import com.cherry.stunner.data.service.AlbumService;
import com.cherry.stunner.data.service.impl.mapper.AlbumMapper;
import com.cherry.stunner.data.service.impl.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

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

    @Override
    public List<Album> getAlbums(long tagId, Long timeOffset, boolean ascending, int limit) {

        final List<Album> albums = new ArrayList<>();

        final List<Long> albumIds = ascending ? albumMapper.selectAlbumIdsAsc(tagId
                , timeOffset != null ? new Date(timeOffset) : null
                , limit)
                :
                albumMapper.selectAlbumIdsAsc(tagId
                        , timeOffset != null ? new Date(timeOffset) : null
                        , limit);

        if (!albumIds.isEmpty()) {
            final Map<Long, Album> albumMap = albumMapper.selectAlbums(albumIds).stream()
                    .collect(toMap(Album::getId, Function.identity()));
            albumIds.forEach(albumId -> {
                Album album = albumMap.get(albumId);
                if (album != null) {
                    albums.add(album);
                }
            });
        }

        return albums;
    }
}
