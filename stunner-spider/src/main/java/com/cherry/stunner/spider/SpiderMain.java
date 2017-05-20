package com.cherry.stunner.spider;

import com.cherry.stunner.spider.m86.M86CategoryTagsProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Spider;

public class SpiderMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        Spider.create(context.getBean(M86CategoryTagsProcessor.class))
                .addUrl("http://www.17786.com/zhifu.html")
                .thread(1)
                .run();

    }

}





