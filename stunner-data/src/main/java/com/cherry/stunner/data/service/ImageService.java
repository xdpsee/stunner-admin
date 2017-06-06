package com.cherry.stunner.data.service;

import com.cherry.stunner.data.enums.ImageStatus;
import com.cherry.stunner.data.po.Image;

import java.util.List;

public interface ImageService {

    long createImage(long albumId, String originTitle, String originUrl);

    int updateImage(long imageId, String title, String url, int width, int height);

    int updateImageStatus(long imageId, ImageStatus status);

    List<Image> getImages(long albumId, Integer limit);

}
