package com.cherry.stunner.data.service.impl.mapper;

import com.cherry.stunner.data.po.Album;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface AlbumMapper {

    Album selectAlbumById(@Param("albumId") long albumId);

    Album selectByOriginUrl(@Param("originUrl") String url);

    Long selectAlbumIdByOriginUrl(@Param("originUrl") String url);

    int insertAlbum(Album album);

    List<Long> selectAlbumIds(@Param("tagId") long tagId
            , @Param("timeOffset") Date timeOffset
            , @Param("ascending") boolean ascending
            , @Param("limit") int limit);

    List<Album> selectAlbums(@Param("albumIds") Collection<Long> albumIds);
}
