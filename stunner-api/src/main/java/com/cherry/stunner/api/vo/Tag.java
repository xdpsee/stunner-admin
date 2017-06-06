package com.cherry.stunner.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Tag {

    private Long id;

    private Integer type;

    private String title;

    private String imageUrl;

    private int imageWidth;

    private int imageHeight;

    private List<AlbumBrief> albums = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlbumBrief {

        private Long id;

        private String title;

        private String coverUrl;

        private Integer coverWidth;

        private Integer coverHeight;

    }

}



