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

    List<Long> selectAlbumIdsAsc(@Param("tagId") long tagId
            , @Param("timeOffset") Date timeOffset
            , @Param("limit") int limit);

    List<Long> selectAlbumIdsDesc(@Param("tagId") long tagId
            , @Param("timeOffset") Date timeOffset
            , @Param("limit") int limit);

    List<Album> selectAlbums(@Param("albumIds") Collection<Long> albumIds);

    int updateAlbumCreateTime(@Param("albumId") long albumId, @Param("gmtCreate") Date createDate);

    List<String> selectAlbumUrls(@Param("offset") int offset, @Param("limit") int limit);

}
