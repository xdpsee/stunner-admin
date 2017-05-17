package com.cherry.stunner.data.service.impl.mapper;

import com.cherry.stunner.data.po.Album;
import com.cherry.stunner.data.po.Category;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CategoryMapper {

    Category selectCategoryById(@Param("cateId") long categoryId);

    Category selectCategoryByTitle(@Param("title") String title);

    int insertCategory(Category category);

    int insertCategoryAlbum(@Param("cateId") long categoryId, @Param("albumId") long albumId, @Param("date") Date date);

    List<Long> selectCategoryAlbums(@Param("cateId") long categoryId
            , @Param("fromDate") Date fromDate
            , @Param("asc") boolean asc
            , @Param("limit") int limit);

}
