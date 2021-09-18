package org.moonlight.redismq.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 功能描述:
 * <p>
 * 主要逻辑:
 * <p>
 * 注意事项:
 *
 * @author Moonlight
 * @date 2021-09-18 16:00
 */
public class PubSubConsumer implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("message received " + message.toString());
    }
}
