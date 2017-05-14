package com.cherry.stunner.data.po;

import com.cherry.stunner.data.enums.ImageStatus;
import lombok.Data;

@Data
public class Image extends Entity {

    private Long albumId;

    private String originTitle;

    private String title;

    private String originUrl;

    private String url;

    private Integer width = 0;

    private Integer height = 0;

    private Integer order = 0;

    private ImageStatus status = ImageStatus.NONE;

}

