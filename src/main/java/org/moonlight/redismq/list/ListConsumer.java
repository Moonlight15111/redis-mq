package org.moonlight.redismq.list;

import org.moonlight.redismq.handler.RedisHandler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 功能描述:
 *   消费指定{@link this#key}的消息
 * 主要逻辑:
 *   通过内部类{@link Listener}起一个线程，死循环的监听指定的{@link this#key}
 * 注意事项:
 *   2021-9-17: 这个东西目前还没有ACK机制，如果取出来之后，没有消费成功，那这个消息就丢了
 *   2021-9-18: 目前实现的是在消费失败之后，将消息又重新塞回原队列里面去
 *              还有一种做法是使用另一个队列pending，来存储取出来的消息，当这个消息消费成功了之后
 *              再将消息从pending队列删掉，并通过定时任务来扫描，看pending队列种是否有消息处理超时的。
 *              个人理解不管怎么样其实都无法真正做到其他MQ的那种ack机制一样，问题的真正源头是在消息存储端
 *
 * @author Moonlight
 * @date 2021-09-17 17:51
 */
public class ListConsumer {

    private RedisHandler redisHandler;
    private String key;
    private Listener listener;

    public ListConsumer(RedisHandler redisHandler, String key) {
        this.redisHandler = redisHandler;
        this.key = key;

        init();
    }

    private void init() {
        this.listener = new Listener();
        this.listener.start();
    }

    public void cancel() {
        this.redisHandler = null;
        this.listener.cancelListener();
    }

    private class Listener extends Thread {
        private AtomicBoolean listen = new AtomicBoolean(true);
        @Override
        public void run() {
            while (listen.get()) {
                Object msg = null;
                try {
                    System.out.println("consumer-" + Thread.currentThread().getName() + " start ... ");
                    msg = redisHandler.blockRightPop(key, 0, TimeUnit.SECONDS);
                    if (msg != null) {
                        System.out.println("consumer-" + Thread.currentThread().getName() + " find something ==> " + msg);
                    }
                } catch (Exception e) {
                    System.out.println("consumer-" + Thread.currentThread().getName() + " exception ");
                    e.printStackTrace();
                    if (msg != null) {
                        redisHandler.leftPush(key, msg);
                    }
                }
            }
        }

        public void cancelListener() {
            listen.set(false);
        }
    }

}