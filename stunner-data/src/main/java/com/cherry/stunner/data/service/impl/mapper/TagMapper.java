package com.cherry.stunner.data.service.impl.mapper;


import com.cherry.stunner.data.po.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {

    List<Tag> selectByImageId(@Param("imageId") long imageId);

    Tag selectById(@Param("tagId") long tagId);

    int insert(Tag tag);


}
