package com.cherry.stunner.spider.m86;

import com.cherry.stunner.data.service.impl.mapper.AlbumMapper;
import com.cherry.stunner.data.service.impl.mapper.TagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class M86PatchAlbumDateProcessor implements PageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(M86CategoryTagsProcessor.class);

    private final Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36")
            .setRetryTimes(6)
            .setSleepTime(1000);

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private AlbumMapper albumMapper;

    private Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})");

    private ThreadLocal<SimpleDateFormat> dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {

        final String pageUrl = page.getRequest().getUrl();

        Selectable dateSpan = page.getHtml().xpath("//html/body/div[@class='falls-detail']/div[@class='content']/div[@class='tsmaincont-desc']/span[1]");
        if (dateSpan.match()) {
            String dateStr = dateSpan.xpath("span/text()").toString();
            Matcher matcher = pattern.matcher(dateStr);
            if (matcher.find()) {
                String str = matcher.group();
                try {
                    final Date date = dateFormat.get().parse(str);

                    Long albumId = albumMapper.selectAlbumIdByOriginUrl(pageUrl);
                    if (albumId != null) {
                        tagMapper.updateAlbumTagTime(albumId, date);
                        albumMapper.updateAlbumCreateTime(albumId, date);
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        logger.error("not match :" + pageUrl);
    }
}
