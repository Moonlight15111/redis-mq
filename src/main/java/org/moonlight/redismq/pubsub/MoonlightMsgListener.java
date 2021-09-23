package org.moonlight.redismq.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能描述:
 * <p>
 * 主要逻辑:
 * <p>
 * 注意事项:
 *
 * @author Moonlight<bzeng @ ibingli.com>
 * @date 2021-09-22 16:06
 */
public class MoonlightMsgListener implements MessageListener {
    AtomicInteger cnt = new AtomicInteger(0);
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cnt.incrementAndGet() + " moonlight listener onMessage message[body: "
                + new String(message.getBody()) + ", topic:"
                + new String(message.getChannel()) + "]pattern["
                + new String(pattern) + "]" );
    }
}