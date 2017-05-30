package com.cherry.stunner.spider;

import com.cherry.stunner.data.service.impl.mapper.AlbumMapper;
import com.cherry.stunner.spider.m86.M86PatchAlbumDateProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

import java.util.ArrayList;
import java.util.List;

public class SpiderMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

//        Spider.create(context.getBean(M86CategoryTagsProcessor.class))
//                .addUrl("http://www.17786.com/meinv/")
//                .setScheduler(new FileCacheQueueScheduler("./").setDuplicateRemover(new HashSetDuplicateRemover()))
//                .thread(1)
//                .run();
//
//        Spider.create(context.getBean(M86ModelTagsProcessor.class))
//                .addUrl("http://www.17786.com/mtdq/")
//                .setScheduler(new FileCacheQueueScheduler("./").setDuplicateRemover(new HashSetDuplicateRemover()))
//                .thread(1)
//                .run();
//
//        Spider.create(context.getBean(M86StarTagsProcessor.class))
//                .addUrl("http://www.17786.com/mxdq/")
//                .setScheduler(new FileCacheQueueScheduler("./").setDuplicateRemover(new HashSetDuplicateRemover()))
//                .thread(1)
//                .run();

        final Spider spider = Spider.create(context.getBean(M86PatchAlbumDateProcessor.class));

        spider.addUrl("http://www.17786.com/3968_1.html",
        "http://www.17786.com/4058_1.html",
        "http://www.17786.com/4223_1.html",
        "http://www.17786.com/4384_1.html",
        "http://www.17786.com/4451_1.html",
        "http://www.17786.com/5416_1.html",
        "http://www.17786.com/5522_1.html",
        "http://www.17786.com/6006_1.html",
        "http://www.17786.com/7890_1.html");

        spider.setScheduler(new FileCacheQueueScheduler("./").setDuplicateRemover(new HashSetDuplicateRemover()))
                .thread(1)
                .run();
    }

}





