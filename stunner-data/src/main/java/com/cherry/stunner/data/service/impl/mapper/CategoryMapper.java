package com.cherry.stunner.data.service.impl.mapper;

import com.cherry.stunner.data.po.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {

    List<Category> selectAll(@Param("status") Integer status);

    Category selectCategoryById(@Param("cateId") long categoryId);

    Category selectCategoryByTitle(@Param("title") String title);

    int insertCategory(Category category);


}


