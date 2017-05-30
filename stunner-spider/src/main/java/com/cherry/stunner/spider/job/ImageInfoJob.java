package com.cherry.stunner.spider.job;

import com.cherry.stunner.data.po.Album;
import com.cherry.stunner.data.service.impl.mapper.AlbumMapper;
import com.cherry.stunner.data.service.impl.mapper.ImageMapper;
import com.cherry.stunner.spider.utils.ImageUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

public class ImageInfoJob {

    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ImageUpload imageUpload;

    public void execute() {

        System.out.println(ImageInfoJob.class.getName() + ", execute");

        processAlbums();

        processImages();

    }

    private void processAlbums() {
        int offset = 0;
        final int limit = 50;
        List<Album> albums;
        do {
            albums = albumMapper.selectNoCoverUrlAlbums(offset, limit);
            albums.forEach(album -> {
                String coverUrl = album.getOriginCoverUrl();

                File temp = null;
                try {
                    String ext = StringUtils.getFilenameExtension(coverUrl);
                    temp = File.createTempFile("stunner-", "." + ext);
                    FileUtils.copyURLToFile(new URL(coverUrl), temp);

                    ImageUpload.HashAndKey result = imageUpload.upload(temp);
                    if (result != null) {
                        BufferedImage image = ImageIO.read(temp);
                        albumMapper.updateAlbumCover(album.getId(), result.getKey(), image.getWidth(), image.getHeight());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (temp != null) {
                        try {
                            FileUtils.forceDelete(temp);
                        } catch (Exception e) {
                            //ignore
                        }
                    }
                }
            });

            offset += limit;
        } while (!albums.isEmpty());

        System.out.println("-->!");
    }

    private void processImages() {



    }
}
