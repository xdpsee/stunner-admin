package com.cherry.stunner.data.service;

import com.cherry.stunner.data.enums.ImageStatus;

public interface ImageService {

    long createImage(long albumId, String originTitle, String originUrl);

    int updateImage(long imageId, String title, String url, int width, int height);

    int updateImageStatus(long imageId, ImageStatus status);

}
