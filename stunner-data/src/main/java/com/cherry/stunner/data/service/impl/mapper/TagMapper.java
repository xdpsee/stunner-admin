package com.cherry.stunner.data.service.impl.mapper;


import com.cherry.stunner.data.po.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TagMapper {

    Tag selectById(@Param("tagId") long tagId);

    Tag select(@Param("type") int type, @Param("title") String title);

    int insert(Tag tag);

    int insertCategoryTag(@Param("cateId") long categoryId
            , @Param("tagId") long tagId
            , @Param("date") Date date);

    List<Tag> selectCategoryTags(@Param("cateId") long categoryId);

    int insertAlbumTag(@Param("albumId") long albumId
            , @Param("tagId") long tagId);

    List<Tag> selectAlbumTags(@Param("albumId") long albumId);

    int updateAlbumTagTime(@Param("albumId") long albumId, @Param("time") Date time);

}
