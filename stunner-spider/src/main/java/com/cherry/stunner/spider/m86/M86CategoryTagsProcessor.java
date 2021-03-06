package com.cherry.stunner.spider.m86;

import com.cherry.stunner.data.enums.TagType;
import com.cherry.stunner.data.service.AlbumService;
import com.cherry.stunner.data.service.CategoryService;
import com.cherry.stunner.data.service.TagService;
import com.cherry.stunner.spider.utils.HttpUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.List;

@Component
public class M86CategoryTagsProcessor implements PageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(M86CategoryTagsProcessor.class);

    private final Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36")
            .setRetryTimes(6)
            .setSleepTime(1000);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private M86ImageProcessor imageProcessor;

    private final HttpUtils httpUtils = new HttpUtils();

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {

        page.getHtml()
                .xpath("//html/body/div[@class='main']/div[@class='container']/ul[@class='hovers']/li")
                .nodes()
                .parallelStream()
                .forEach(categoryLi -> {

                    String caption = categoryLi.xpath("h3/text()").toString();
                    System.out.println(caption);

                    long categoryId = categoryService.createCategory(caption);

                    if (!StringUtils.isEmpty(caption)) {
                        List<Selectable> tagList = categoryLi.xpath("ul/li").nodes();
                        tagList.forEach(tag -> {
                            String tagHref = tag.xpath("a/@href").toString();
                            String tagText = tag.xpath("a/text()").toString();
                            if (!StringUtils.isEmpty(tagText) && !StringUtils.isEmpty(tagHref)) {

                                long tagId = tagService.createTag(TagType.NORMAL, tagText);

                                tagService.bindCategoryTag(categoryId, tagId);
                                page.getTargetRequests().add(new Request(tagHref));
                            }
                        });
                    }
                });

        Selectable albumContainer = page.getHtml().xpath("//html/body/div[@class='falls-detail']/div[@class='falls_container']");
        if (albumContainer.match()) {
            processAlbumPage(page, albumContainer);
        }

        Selectable imageContent = page.getHtml().xpath("//html/body/div[@class='falls-detail']/div[@class='content']");
        if (imageContent.match()) {
            imageProcessor.process(page, imageContent);
        }
    }

    private void processAlbumPage(Page page, Selectable albumContainer) {

        Selectable tagNavLink = page.getHtml().xpath("//html/body/div[@class='falls-detail']/div[@class='tags']/a[2]");
        if (!tagNavLink.match()) {
            logger.error("tagNavLink not found for " + page.getRequest().getUrl());
            return;
        }

        String tagRelativeHref = tagNavLink.xpath("a/@href").toString();
        int subIndex = tagRelativeHref.lastIndexOf("/");
        final String tagKeyWord = tagRelativeHref.substring(subIndex).replace("/", "").replace(".html", "");

        long tagId = 0;
        final String tagText = tagNavLink.xpath("a/span[@style='color:red']/text()").toString();
        if (tagText != null) {
            tagId = tagService.createTag(TagType.NORMAL, tagText);
        }

        List<Selectable> albumList = albumContainer.xpath("ul[@class='col']/li[@class='falls']").nodes();

        saveAlbum(page, albumList, tagId);

        Selectable moreLink = albumContainer.xpath("div[@class='veiwall']/a[@id='veiwall']");
        if (moreLink.match()) {
            processMoreAlbums(page, tagId, tagKeyWord, 2);
        }
    }

    private void processMoreAlbums(final Page page, long tagId, String tagKeyWord, int pageNo) {

        final String url = String.format(HttpUtils.MORE_ALBUMS_REQUEST_FORMAT, tagKeyWord, pageNo, RandomUtils.nextDouble());

        int retryTimes = 10;
        do {
            try {
                System.out.println(url);

                String response = httpUtils.get(url);
                if (!StringUtils.isEmpty(response)) {
                    Html moreHtml = new Html(response);
                    List<Selectable> albumList = moreHtml.xpath("//html/body/li[@class='falls']").nodes();

                    saveAlbum(page, albumList, tagId);

                    if (!albumList.isEmpty()) {
                        processMoreAlbums(page, tagId, tagKeyWord, ++pageNo);
                    }
                }
                //
                break;

            } catch (IOException e) {
                retryTimes--;
                e.printStackTrace();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ei) {
                    ei.printStackTrace();
                }
            }
        } while (retryTimes > 0);
    }

    private void saveAlbum(final Page page, List<Selectable> albumList, long tagId) {

        albumList.parallelStream().forEach(albumLi -> {
            String albumUrl = albumLi.xpath("a[@class='img']/@href").toString();
            String coverUrl = albumLi.xpath("a[@class='img']/img/@src").toString();
            String albumTitle = albumLi.xpath("a[@class='img']/img/@alt").toString();

            if (null == albumUrl || null == coverUrl || null == albumTitle) {
                logger.error("error parse album: " + albumLi.toString());
            } else {
                long albumId = albumService.createAlbum(albumUrl, albumTitle, coverUrl);
                if (tagId > 0) {
                    tagService.bindAlbumTag(albumId, tagId);
                }

                page.addTargetRequest(albumUrl);
            }
        });

    }

}
