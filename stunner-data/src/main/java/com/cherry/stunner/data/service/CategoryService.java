package com.cherry.stunner.data.service;

import com.cherry.stunner.data.enums.CategoryStatus;

public interface CategoryService {


    long createCategory(String category);

    boolean updateCategoryStatus(long categoryId, CategoryStatus status);



}
