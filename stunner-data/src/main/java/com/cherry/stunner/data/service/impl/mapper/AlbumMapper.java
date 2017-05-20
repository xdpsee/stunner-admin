package com.cherry.stunner.data.service.impl.mapper;

import com.cherry.stunner.data.po.Album;
import org.apache.ibatis.annotations.Param;

public interface AlbumMapper {

    Album selectAlbumById(@Param("albumId") long albumId);

    Album selectByOriginUrl(@Param("originUrl") String url);

    Long selectAlbumIdByOriginUrl(@Param("originUrl") String url);

    int insertAlbum(Album album);

}
