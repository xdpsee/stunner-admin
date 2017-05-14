package com.cherry.stunner.data.po;

import com.cherry.stunner.data.enums.CategoryStatus;
import lombok.Data;

/**
 * Createdby Administrator on 2017/5/14 0014.
 */
@Data
public class Category extends Entity {

    private String title;

    private Integer albums;

    private CategoryStatus status;

}
