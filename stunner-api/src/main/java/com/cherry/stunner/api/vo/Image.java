package com.cherry.stunner.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    private Long id;

    private String imageUrl;

    private Integer imageWidth;

    private Integer imageHeight;

}
