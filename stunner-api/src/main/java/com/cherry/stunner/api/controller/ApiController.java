package com.cherry.stunner.api.controller;

import com.cherry.stunner.api.common.ResponseData;
import com.cherry.stunner.api.utils.ListUtils;
import com.cherry.stunner.api.vo.AlbumList;
import com.cherry.stunner.api.vo.Category;
import com.cherry.stunner.api.vo.Image;
import com.cherry.stunner.api.vo.Tag;
import com.cherry.stunner.data.po.Album;
import com.cherry.stunner.data.service.AlbumService;
import com.cherry.stunner.data.service.CategoryService;
import com.cherry.stunner.data.service.ImageService;
import com.cherry.stunner.data.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ImageService imageService;

    @ResponseBody
    @RequestMapping("/categories")
    public ResponseData<List<Category>> listCategories() {

        List<com.cherry.stunner.data.po.Category> categories = categoryService.listCategories(null);

        return ResponseData.success(categories.stream()
                .map(c -> new Category(c.getId(), c.getTitle()))
                .collect(toList())
        );
    }

    @ResponseBody
    @RequestMapping("/category/{categoryId}/tags")
    public ResponseData<List<Tag>> listCategoryTags(@PathVariable("categoryId") Long categoryId) {

        List<com.cherry.stunner.data.po.Tag> tags = tagService.getCategoryTags(categoryId);

        return ResponseData.success(tags.stream()
                .map(t -> {
                    Tag tag = new Tag();
                    tag.setId(t.getId());
                    tag.setType(t.getType().code);

                    List<Album> albums = albumService.getAlbums(t.getId(), null, false, 9);

                    tag.setAlbums(albums.stream().map(a -> new Tag.AlbumBrief(a.getId()
                            , a.getOriginTitle()
                            , a.getOriginCoverUrl()
                            , a.getCoverWidth()
                            , a.getCoverHeight())).collect(toList()));

                    return tag;
                })
                .collect(toList())
        );

    }

    @ResponseBody
    @RequestMapping("/tag/{tagId}/albums")
    public ResponseData<AlbumList> listAlbums(@PathVariable("tagId") Long tagId
            , @RequestParam(value = "timeOffset", required = false) Long timeOffset
            , @RequestParam(value = "ascending", required = false, defaultValue = "false") boolean ascending
            , @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {

        List<Album> albums = albumService.getAlbums(tagId, timeOffset, ascending, limit);

        List<com.cherry.stunner.api.vo.Album> list = albums.stream()
                .map(e -> {
                    com.cherry.stunner.api.vo.Album album = new com.cherry.stunner.api.vo.Album(e.getId()
                            , e.getGmtCreate().getTime()
                            , e.getOriginTitle()
                            , e.getOriginCoverUrl()
                            , e.getCoverWidth()
                            , e.getCoverHeight()
                            , new ArrayList<>());

                    List<com.cherry.stunner.data.po.Image> images = imageService.getImages(album.getId(), 9);
                    album.setPreviews(images.stream()
                            .map(m -> new Image(m.getId(), m.getOriginUrl(), m.getWidth(), m.getHeight()))
                            .collect(toList())
                    );
                    return album;
                }).collect(toList());

        AlbumList result = new AlbumList();
        result.setAlbums(list);
        Optional<com.cherry.stunner.api.vo.Album> last = ListUtils.last(list);
        result.setNextTimeOffset(last.map(com.cherry.stunner.api.vo.Album::getGmtCreate).orElse(null));

        return ResponseData.success(result);

    }
}



