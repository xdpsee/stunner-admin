package com.cherry.stunner.api.utils;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ListUtils {

    public static <T> Optional<T> last(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Optional.empty();
        }

        return Optional.of(list.get(list.size() - 1));
    }

}
