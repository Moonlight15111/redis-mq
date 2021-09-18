package org.moonlight.redismq.list;

import org.moonlight.redismq.handler.RedisHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 *   管理消费者
 * 主要逻辑:
 *
 * 注意事项:
 *
 * @author moonlight
 * @date 2021-09-17 18:13
 */
public final class ListConsumerManager {

    private ListConsumerManager() {}

    private static Map<String, ListConsumer> consumers = new HashMap<>();

    /**
     * 功能描述:
     * 主要逻辑:
     * 注意事项:
     * @param key 消费者订阅的队列的key
     * @param redisHandler redis小助手
     * @author moonlight
     * @date 2021/9/18 9:08
     **/
    public static void createConsumer(String key, RedisHandler redisHandler) {
        if (!consumers.containsKey(key)) {
            consumers.put(key, new ListConsumer(redisHandler, key));
        }
    }

    public static void cancelConsumer(String key) {
        if (consumers.containsKey(key)) {
            ListConsumer remove = consumers.remove(key);
            remove.cancel();
        }
    }

}