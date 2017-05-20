package com.cherry.stunner.spider;

import com.cherry.stunner.spider.m86.M86CategoryTagsProcessor;
import com.cherry.stunner.spider.m86.M86ModelTagsProcessor;
import com.cherry.stunner.spider.m86.M86StarTagsProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Spider;

public class SpiderMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

//        Spider.create(context.getBean(M86CategoryTagsProcessor.class))
//                .addUrl("http://www.17786.com/meinv/")
//                .thread(1)
//                .run();

//        Spider.create(context.getBean(M86ModelTagsProcessor.class))
//                .addUrl("http://www.17786.com/mtdq/")
//                .thread(1)
//                .run();

        Spider.create(context.getBean(M86StarTagsProcessor.class))
                .addUrl("")
                .thread(1)
                .run();

    }

}





