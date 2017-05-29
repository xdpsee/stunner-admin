package com.cherry.stunner.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private Long id;

    private Integer type;

    private String title;

    private String imageUrl;

    private int imageWidth;

    private int imageHeight;

}
