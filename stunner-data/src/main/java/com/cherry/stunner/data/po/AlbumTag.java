package com.cherry.stunner.data.po;

import lombok.Data;

import java.util.Date;

@Data
public class AlbumTag {

    private Long albumId;

    private Long tagId;

    private Date gmtCreate;

}
