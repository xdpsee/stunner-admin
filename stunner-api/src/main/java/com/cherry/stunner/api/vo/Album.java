package com.cherry.stunner.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    private Long id;

    private Long gmtCreate;

    private String title;

    private String coverUrl;

    private Integer coverWidth;

    private Integer coverHeight;

}
