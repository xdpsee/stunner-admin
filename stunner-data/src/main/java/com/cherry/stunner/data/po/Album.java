package com.cherry.stunner.data.po;

import com.cherry.stunner.data.enums.AlbumStatus;
import lombok.Data;

@Data
public class Album extends Base {

    private String originTitle;

    private String title;

    private String originImageUrl;

    private String imageUrl;

    private Integer imageWidth = 0;

    private Integer imageHeight = 0;

    private Integer imageCount = 0;

    private AlbumStatus status = AlbumStatus.NONE;

}

