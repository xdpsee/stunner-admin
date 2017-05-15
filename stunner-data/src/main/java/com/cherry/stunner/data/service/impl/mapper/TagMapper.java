package com.cherry.stunner.data.service.impl.mapper;


import com.cherry.stunner.data.po.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {




    Tag selectById(@Param("tagId") long tagId);

    int insert(Tag tag);

    List<Tag> selectByImageId(@Param("imageId") long imageId);

    int insertImageTag(@Param("imageId") long imageId, @Param("tagId") long tagId);

    

}
