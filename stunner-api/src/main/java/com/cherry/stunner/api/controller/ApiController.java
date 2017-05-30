package com.cherry.stunner.api.controller;

import com.cherry.stunner.api.utils.JSONUtil;
import com.cherry.stunner.api.common.ResponseData;
import com.cherry.stunner.api.utils.ListUtils;
import com.cherry.stunner.api.vo.ro.ListAlbumsRO;
import com.cherry.stunner.api.vo.AlbumList;
import com.cherry.stunner.api.vo.Category;
import com.cherry.stunner.api.vo.Tag;
import com.cherry.stunner.data.po.Album;
import com.cherry.stunner.data.service.AlbumService;
import com.cherry.stunner.data.service.CategoryService;
import com.cherry.stunner.data.service.TagService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                .map(t -> new Tag(t.getId(), t.getType().code, t.getTitle(), "", 0, 0))
                .collect(toList())
        );

    }

    @ResponseBody
    @RequestMapping("/tag/{tagId}/albums")
    public ResponseData<AlbumList> listAlbums(@PathVariable("tagId") Long tagId, @RequestParam("params") String query) {

        ListAlbumsRO ro = JSONUtil.fromJsonString(query, new TypeReference<ListAlbumsRO>(){});
        if (null == ro) {
            return ResponseData.error("参数错误");
        }

        List<Album> albums = albumService.getAlbums(tagId, ro.getTimeOffset(), ro.getAscending(), ro.getLimit());

        List<com.cherry.stunner.api.vo.Album> list = albums.stream()
                .map(e -> new com.cherry.stunner.api.vo.Album(e.getId()
                        , e.getGmtCreate().getTime()
                        , e.getOriginTitle()
                        , e.getOriginCoverUrl()
                        , e.getCoverWidth()
                        , e.getCoverHeight()))
                .collect(toList());

        AlbumList result = new AlbumList();
        result.setAlbums(list);
        Optional<com.cherry.stunner.api.vo.Album> last = ListUtils.last(list);
        result.setNextTimeOffset(last.map(com.cherry.stunner.api.vo.Album::getGmtCreate).orElse(null));

        return ResponseData.success(result);

    }
}
