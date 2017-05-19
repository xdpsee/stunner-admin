package com.cherry.stunner.data.service;

import com.cherry.stunner.data.enums.TagType;
import com.cherry.stunner.data.po.Tag;

import java.util.List;

public interface TagService {

    long createTag(TagType type, String title);

    List<Tag> getCategoryTags(long category);

    List<Tag> getAlbumTags(long album);

}

