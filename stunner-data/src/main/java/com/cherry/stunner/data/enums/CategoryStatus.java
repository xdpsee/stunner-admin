package com.cherry.stunner.data.enums;

import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/5/14 0014.
 */
public enum CategoryStatus {

    HIDE(0, "隐藏"),
    SHOW(1, "显示"),
    ;

    public final int code;
    public final String comment;

    CategoryStatus(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public static CategoryStatus valueOf(int code) {
        return Stream.of(values())
                .filter(s -> s.code == code).findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
