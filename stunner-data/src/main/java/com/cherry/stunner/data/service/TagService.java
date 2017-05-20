package com.cherry.stunner.data.service;

import com.cherry.stunner.data.enums.TagType;
import com.cherry.stunner.data.po.Tag;

import java.util.List;

public interface TagService {

    long createTag(TagType type, String title);

    void bindCategoryTag(long categoryId, long tagId);

    List<Tag> getCategoryTags(long category);

    void bindAlbumTag(long albumId, long tagId);

    List<Tag> getAlbumTags(long album);

}

