package com.cherry.stunner.data.service.impl.service;

import com.cherry.stunner.data.enums.CategoryStatus;
import com.cherry.stunner.data.po.Category;
import com.cherry.stunner.data.service.CategoryService;
import com.cherry.stunner.data.service.impl.mapper.CategoryMapper;
import com.cherry.stunner.data.service.impl.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public long createCategory(String title) {

        Category category = new Category();
        category.setTitle(title);

        try {
            categoryMapper.insertCategory(category);
        } catch (Exception e) {
            if (!ExceptionUtils.hasDuplicateEntryException(e)) {
                throw e;
            }

            category = categoryMapper.selectCategoryByTitle(title);
        }

        return category.getId();
    }

    @Override
    public boolean updateCategoryStatus(long categoryId, CategoryStatus status) {
        return false;
    }
}
