package com.cherry.stunner.data.enums;

import java.util.stream.Stream;

public enum TagType {

    NORMAL(0, "普通"),
    STAR(1, "明星"),
    MODEL(2, "模特"),
    ;

    public final int code;
    public final String comment;

    TagType(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public static TagType valueOf(int code) {
        return Stream.of(values())
                .filter(s -> s.code == code).findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
