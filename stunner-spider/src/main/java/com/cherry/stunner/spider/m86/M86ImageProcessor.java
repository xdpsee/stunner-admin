package com.cherry.stunner.spider.m86;

import com.cherry.stunner.data.service.AlbumService;
import com.cherry.stunner.data.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class M86ImageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(M86ImageProcessor.class);

    @Autowired
    private AlbumService albumService;
    @Autowired
    private ImageService imageService;

    public void process(Page page, Selectable imageContent) {

        String titleText = imageContent.xpath("div[@class='pageheader']/h2/text()").toString();
        if (null == titleText) {
            logger.error("titleText not found");
        }

        Selectable imageBox = imageContent.xpath("div[@class='img_box']");
        if (!imageBox.match()) {
            logger.error("imageBox not found");
            return;
        }

        String title = StringUtils.trimLeadingWhitespace(titleText);
        title = StringUtils.trimTrailingWhitespace(title);

        final String pageUrl = page.getRequest().getUrl();
        Matcher matcher = PATTERN_CURR.matcher(pageUrl);
        if (!matcher.find()) {
            logger.error("error image box url: " + pageUrl);
            return;
        }

        matcher = PATTERN_ALL.matcher(title);
        if (!matcher.find()) {
            logger.error("imageBox progress not found");
            return;
        }

        String albumTitle = title.replaceAll(PATTERN_ALL.pattern(), "");
        String albumUrl = pageUrl.replaceAll(PATTERN_CURR.pattern(), "_1.html");
        final Long albumId =  albumService.existAlbumByUrl(albumUrl);
        if (albumId == null) {
            logger.error(String.format("imageBox error, %s, %s, %s", pageUrl, albumTitle, albumUrl));
            return;
        }

        String progress = matcher.group().replaceAll("(\\()|(\\))", "");
        String[] components = progress.split("/");
        final Long count = Long.parseLong(components[1]);
        Set<String> imageBoxUrls = new HashSet<>();
        for (int i = 1; i <= count; ++i) {
            String imageBoxUrl = pageUrl.replaceAll(PATTERN_CURR.pattern(), String.format("_%d.html", i));
            imageBoxUrls.add(imageBoxUrl);
        }
        imageBoxUrls.remove(pageUrl);
        imageBoxUrls.forEach(url -> page.getTargetRequests().add(new Request(url)));

        System.out.println(pageUrl);

        Selectable img = imageContent.xpath("div[@class='img_box']/a/img[@class='IMG_show']");
        if (!img.match()) {
            img = imageContent.xpath("div[@class='img_box']/img[@class='IMG_show']");
        }
        if (!img.match()) {
            logger.error("img not found");
            return;
        }

        String imageUrl = img.xpath("img/@src").toString();
        long imageId = imageService.createImage(albumId, title, imageUrl);
        System.out.println(String.format("%d, %s", imageId, pageUrl));
    }

    private final Pattern PATTERN_ALL = Pattern.compile("(\\(\\d+\\/\\d+\\))");

    private final Pattern PATTERN_CURR = Pattern.compile("(_\\d+\\.html$)");
}
