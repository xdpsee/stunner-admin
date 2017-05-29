package com.cherry.stunner.data.service;

import com.cherry.stunner.data.enums.CategoryStatus;
import com.cherry.stunner.data.po.Category;

import java.util.List;

public interface CategoryService {


    long createCategory(String category);

    boolean updateCategoryStatus(long categoryId, CategoryStatus status);

    List<Category> listCategories(CategoryStatus status);

}
