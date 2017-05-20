package com.cherry.stunner.data.service.impl.service;

import com.cherry.stunner.data.enums.TagType;
import com.cherry.stunner.data.po.Tag;
import com.cherry.stunner.data.service.TagService;
import com.cherry.stunner.data.service.impl.mapper.TagMapper;
import com.cherry.stunner.data.service.impl.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public long createTag(TagType type, String title) {

        Tag tag = new Tag();
        tag.setType(type);
        tag.setTitle(title);
        try {
            tagMapper.insert(tag);
        } catch (Exception e) {
            if (!ExceptionUtils.hasDuplicateEntryException(e)) {
                throw e;
            }

            tag = tagMapper.select(type.code, title);
        }

        return tag.getId();
    }

    @Override
    public void bindCategoryTag(long categoryId, long tagId) {

        tagMapper.insertCategoryTag(categoryId, tagId, new Date());
    }

    @Override
    public List<Tag> getCategoryTags(long categoryId) {
        return tagMapper.selectCategoryTags(categoryId);
    }

    @Override
    public void bindAlbumTag(long albumId, long tagId) {
        tagMapper.insertAlbumTag(albumId, tagId);
    }

    @Override
    public List<Tag> getAlbumTags(long albumId) {
        return tagMapper.selectAlbumTags(albumId);
    }
}
