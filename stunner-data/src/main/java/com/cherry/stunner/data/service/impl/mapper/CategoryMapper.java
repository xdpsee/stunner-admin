package com.cherry.stunner.data.service.impl.mapper;

import com.cherry.stunner.data.po.Category;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {

    Category selectById(@Param("catId") long categoryId);

    Category selectByTitle(@Param("title") String title);

    int insert(Category category);

}
