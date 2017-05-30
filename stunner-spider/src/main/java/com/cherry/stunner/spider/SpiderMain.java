package com.cherry.stunner.spider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    }

}





