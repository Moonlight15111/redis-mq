package org.moonlight.redismq.list;

import org.moonlight.redismq.handler.RedisHandler;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 * <p>
 * 主要逻辑:
 * <p>
 * 注意事项:
 *
 * @author Moonlight
 * @date 2021-09-17 17:51
 */
public class Consumer {

    private RedisHandler redisHandler;
    private String key;
    private Lintener lintener;

    public Consumer(RedisHandler redisHandler, String key) {
        this.redisHandler = redisHandler;
        this.key = key;

        init();
    }

    private void init() {
        this.lintener = new Lintener();
        this.lintener.start();
    }

    private class Lintener extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("consumer-" + Thread.currentThread().getName() + " start ... ");
                    // 这个地方，如果使用nonblock，当redis中的数据为空的时候，会一直轮询，消耗资源
                    // 如果使用block，
                    Object o = redisHandler.blockRightPop(key, 0, TimeUnit.SECONDS);
                    if (o != null) {
                        System.out.println("consumer-" + Thread.currentThread().getName() + " find something ==> " + o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}