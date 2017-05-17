package com.cherry.stunner.data.po;

import com.cherry.stunner.data.enums.AlbumStatus;
import lombok.Data;

@Data
public class Album extends Entity {

    private String originTitle;

    private String title;

    private String originCoverUrl;

    private String coverUrl;

    private Integer coverWidth = 0;

    private Integer coverHeight = 0;

    private Integer images = 0;

    private AlbumStatus status = AlbumStatus.NONE;

}

