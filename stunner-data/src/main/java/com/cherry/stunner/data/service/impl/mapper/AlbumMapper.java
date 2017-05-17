package com.cherry.stunner.data.service.impl.mapper;

import com.cherry.stunner.data.po.Album;
import org.apache.ibatis.annotations.Param;

public interface AlbumMapper {

    Album selectAlbumById(@Param("albumId") long albumId);

    int insertAlbum(Album album);

}
