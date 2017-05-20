package com.cherry.stunner.data.po;

import com.cherry.stunner.data.enums.CategoryStatus;
import lombok.Data;

@Data
public class Category extends Entity {

    private String title;

    private Integer tags = 0;

    private CategoryStatus status;

}
