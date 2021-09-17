package org.moonlight.redismq.list;

import org.moonlight.redismq.handler.RedisHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 * <p>
 * 主要逻辑:
 * <p>
 * 注意事项:
 *
 * @author
 * @date 2021-09-17 18:13
 */
public final class ConsumerManager {

    private ConsumerManager() {}

    private static Map<String, Consumer> consumers = new HashMap<>();

    public static void createConsumer(String key, RedisHandler redisHandler) {
        if (!consumers.containsKey(key)) {
            consumers.put(key, new Consumer(redisHandler, key));
        }
    }

}