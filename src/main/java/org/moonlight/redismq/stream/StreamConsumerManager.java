package org.moonlight.redismq.stream;

import org.moonlight.redismq.handler.RedisHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
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
 * @date 2021-09-22 17:33
 */
@Component("streamConsumerManager")
public class StreamConsumerManager {

    private RedisTemplate<String, Object> redisTemplate;
    private Map<String, StreamConsumer> map;

    @Autowired
    public StreamConsumerManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.map = new HashMap<>();
    }

    public void createConsumer(String streamName) {
        if (!map.containsKey(streamName)) {
            redisTemplate.opsForStream().createGroup(streamName, "consumer");
            map.put(streamName, new StreamConsumer("consumer", streamName, redisTemplate));
            map.get(streamName).start();
        }
    }

    public void product(String streamName) {
        Map<String, String> m = new HashMap<>(8);
        m.put("time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        redisTemplate.opsForStream().add(streamName, m);
    }

}
