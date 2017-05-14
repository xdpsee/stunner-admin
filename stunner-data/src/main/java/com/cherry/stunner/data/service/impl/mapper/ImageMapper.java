package com.cherry.stunner.data.service.impl.mapper;

import com.cherry.stunner.data.enums.ImageStatus;
import com.cherry.stunner.data.po.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImageMapper {

    List<Image> selectByAlbumId(@Param("albumId") Long albumId);

    List<Image> selectUnreadyByAlbumId(@Param("albumId") Long albumId);

    int insert(Image image);

    int updateImage(@Param("imageId") long imageId
            , @Param("title") String title
            , @Param("url") String url
            , @Param("width") Integer width
            , @Param("height") Integer height);

    int updateStatus(@Param("imageId") long imageId
            , @Param("status") ImageStatus status);


}
