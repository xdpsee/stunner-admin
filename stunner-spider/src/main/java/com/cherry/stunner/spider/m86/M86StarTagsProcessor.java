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
import java.util.ArrayList;
import java.util.List;

@Component
public class M86StarTagsProcessor implements PageProcessor {

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
                .forEach(e -> {
                    List<Selectable> modelLinks = e.xpath("ul/li").nodes();
                    modelLinks.forEach(modelLink -> {
                        String tagText = modelLink.xpath("a/text()").toString();
                        String tagHref = modelLink.xpath("a/@href").toString();

                        if (tagText != null && tagHref != null) {

                            System.out.println(tagText + ", " + tagHref);

                            tagService.createTag(TagType.STAR, tagText);
                            page.getTargetRequests().add(new Request(tagHref));
                        }
                    });

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

        final List<Long> tagIds = new ArrayList<>();
        long tagId = tagService.createTag(TagType.NORMAL, "明星");
        tagIds.add(tagId);

        final String tagText = tagNavLink.xpath("a/span[@style='color:red']/text()").toString();
        if (tagText != null) {
            tagId = tagService.createTag(TagType.STAR, tagText);
            tagIds.add(tagId);
        }

        List<Selectable> albumList = albumContainer.xpath("ul[@class='col']/li[@class='falls']").nodes();

        saveAlbum(page, albumList, tagIds);

        Selectable moreLink = albumContainer.xpath("div[@class='veiwall']/a[@id='veiwall']");
        if (moreLink.match()) {
            processMoreAlbums(page, tagIds, tagKeyWord, 2);
        }
    }

    private void processMoreAlbums(final Page page, final List<Long> tagIds, final String tagKeyWord, int pageNo) {

        final String url = String.format(HttpUtils.MORE_ALBUMS_REQUEST_FORMAT
                , tagKeyWord, pageNo, RandomUtils.nextDouble());

        int retryTimes = 6;
        do {
            try {
                System.out.println(url);

                String response = httpUtils.get(url);
                if (!StringUtils.isEmpty(response)) {
                    Html moreHtml = new Html(response);
                    List<Selectable> albumList = moreHtml.xpath("//html/body/li[@class='falls']").nodes();

                    saveAlbum(page, albumList, tagIds);

                    if (!albumList.isEmpty()) {
                        processMoreAlbums(page, tagIds, tagKeyWord, ++pageNo);
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

    private void saveAlbum(Page page, List<Selectable> albumList, List<Long> tagIds) {
        albumList.parallelStream().forEach(albumLi -> {
            String albumUrl = albumLi.xpath("a[@class='img']/@href").toString();
            String coverUrl = albumLi.xpath("a[@class='img']/img/@src").toString();
            String albumTitle = albumLi.xpath("a[@class='img']/img/@alt").toString();

            if (null == albumUrl || null == coverUrl || null == albumTitle) {
                logger.error("error parse album: " + albumLi.toString());
            } else {
                long albumId = albumService.createAlbum(albumUrl, albumTitle, coverUrl);
                tagIds.forEach(tagId -> tagService.bindAlbumTag(albumId, tagId));

                page.addTargetRequest(albumUrl);
            }
        });
    }



}
