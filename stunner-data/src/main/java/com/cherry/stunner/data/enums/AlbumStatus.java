package com.cherry.stunner.data.enums;

import java.util.stream.Stream;

/**
 * Created by chanjerry on 2017/5/13.
 */
public enum AlbumStatus {

    NONE(0, "初始状态,尚未处理,不可使用"),
    ERROR(1, "图片处理出错,不可使用"),
    READY(2, "专辑处理完成,待上线"),
    ONLINE(2, "已上线"),
    ;

    public final int code;
    public final String comment;

    AlbumStatus(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public static AlbumStatus valueOf(int code) {
        return Stream.of(values())
                .filter(s -> s.code == code).findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
