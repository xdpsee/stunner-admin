package com.cherry.stunner.api.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AlbumList {

    private Long nextTimeOffset;

    List<Album> albums = new ArrayList<>();

}
