package com.cherry.stunner.data.enums;

import java.util.stream.Stream;

public enum ImageStatus {
    NONE(0, "初始状态,尚未处理,不可使用"),
    ERROR(1, "图片处理出错,不可使用"),
    READY(2, "处理完成,可以使用"),
    ;

    public final int code;
    public final String comment;

    ImageStatus(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public static ImageStatus valueOf(int code) {
        return Stream.of(values())
                .filter(s -> s.code == code).findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
