package com.cherry.stunner.data.service.impl.service;

import com.cherry.stunner.data.enums.ImageStatus;
import com.cherry.stunner.data.po.Image;
import com.cherry.stunner.data.service.ImageService;
import com.cherry.stunner.data.service.impl.mapper.ImageMapper;
import com.cherry.stunner.data.service.impl.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public long createImage(long albumId, String originTitle, String originUrl) {

        Image image = new Image();
        image.setAlbumId(albumId);
        image.setOriginTitle(originTitle);
        image.setOriginUrl(originUrl);
        image.setTitle(originTitle);

        try {
            imageMapper.insert(image);
        } catch (Exception e) {
            if (!ExceptionUtils.hasDuplicateEntryException(e)) {
                throw e;
            }

            return imageMapper.selectIdByAlbumIdAndOriginUrl(albumId, originUrl);
        }

        return image.getId();
    }

    @Override
    public int updateImage(long imageId, String title, String url, int width, int height) {
        return 0;
    }

    @Override
    public int updateImageStatus(long imageId, ImageStatus status) {
        return 0;
    }

    @Override
    public List<Image> getImages(long albumId, Integer limit) {

        return imageMapper.selectByAlbumId(albumId, limit);

    }
}
