package com.cherry.stunner.api.vo.ro;

import lombok.Data;

@Data
public class ListAlbumsRO {

    private Long timeOffset;

    private Boolean ascending = false;

    private Integer limit = 20;

}
