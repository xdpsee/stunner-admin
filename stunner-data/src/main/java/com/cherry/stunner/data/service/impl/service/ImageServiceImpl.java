package com.cherry.stunner.data.service.impl.service;

import com.cherry.stunner.data.enums.ImageStatus;
import com.cherry.stunner.data.service.ImageService;
import org.springframework.stereotype.Component;

@Component
public class ImageServiceImpl implements ImageService {

    @Override
    public long createImage(long albumId, String originTitle, String originUrl) {
        return 0;
    }

    @Override
    public int updateImage(long imageId, String title, String url, int width, int height) {
        return 0;
    }

    @Override
    public int updateImageStatus(long imageId, ImageStatus status) {
        return 0;
    }
}
