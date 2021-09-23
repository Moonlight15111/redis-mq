package org.moonlight.redismq.stream;

import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 功能描述:
 * <p>
 * 主要逻辑:
 * <p>
 * 注意事项:
 *
 * @author Moonlight
 * @date 2021-09-22 17:49
 */
public class StreamConsumer extends Thread {

    private String groupName, streamName;

    private RedisTemplate<String, Object> redisTemplate;

    private AtomicBoolean running = new AtomicBoolean(true);

    public StreamConsumer(String groupName, String streamName, RedisTemplate<String, Object> redisTemplate) {
        this.groupName = groupName;
        this.streamName = streamName;
        this.redisTemplate = redisTemplate;
    }

    public void stopConsume() {
        running.set(false);
    }

    @Override
    public void run() {
        // 读取设置
        StreamReadOptions options = StreamReadOptions.empty();
        // 设置堵塞读取多少时间
        options = options.block(Duration.ofHours(1));
        // 设置读取数目
        options = options.count(1);
        // 设置自动提交
        options = options.autoAcknowledge();
        // 定义消费者，第一个参数是消费者群组，第二个参数是群组里面的消费者
        Consumer consumer = Consumer.from(groupName, streamName);
        // 定义从什么stream读，从stream的什么位置读，$是latest(),>是lastConsumerd() 且只在群组读取才能使用。
        StreamOffset<String> offset = StreamOffset.create(streamName, ReadOffset.lastConsumed());
        List<MapRecord<String, Object, Object>> read;
        while(running.get()) {
            read = redisTemplate.opsForStream().read(consumer, options, offset);
            if (read != null) {
                for (MapRecord<String, Object, Object> m : read) {
                    System.out.println(Thread.currentThread().getName() + " consume msg{" + m.toString() + "}");
                }
            }
        }
    }

}
