package org.moonlight.redismq.pubsub;

import org.moonlight.redismq.handler.RedisHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 * <p>
 * 主要逻辑:
 * <p>
 * 注意事项:
 *
 * @author Moonlight
 * @date 2021-09-18 15:56
 */
@Component("producer")
public class Producer {

    private RedisHandler redisHandler;

    @Autowired
    public Producer(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }

    public void publish(String topic) {
        String msg = Thread.currentThread().getName() + "_" + Thread.currentThread().getId() + "_" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        redisHandler.publish(msg, topic);
    }

}