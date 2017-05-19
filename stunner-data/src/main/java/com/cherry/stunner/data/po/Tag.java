package com.cherry.stunner.data.po;

import com.cherry.stunner.data.enums.TagType;
import lombok.Data;

@Data
public class Tag extends Entity {

    private TagType type;

    private String title;

    private Integer albums;
    
}



