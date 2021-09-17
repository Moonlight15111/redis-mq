package org.moonlight.redismq.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 *   redis操作小助手，在本项目中只是对{@link org.springframework.data.redis.core.RedisTemplate}的几个方法进行了简单的封装
 *   用来实现基于redis的消息队列
 * 主要逻辑:
 *
 * 注意事项:
 *
 * @author Moonlight
 * @date 2021-09-17 15:27
 */
@Component("redisHandler")
public class RedisHandler {

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 功能描述: redis的lpush命令，将一个或多个值插入到列表头部。 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
     *          当 key 存在但不是列表类型时，返回一个错误。
     * 注意事项: 在Redis 2.4版本以前的 LPUSH 命令，都只接受单个 value 值。
     * @param key 键
     * @param vals 值，不定参，可能为多个也可能为一个
     * @return boolean true 表示存入redis成功，false 表示存入redis失败
     * @author moonlight
     * @date 2021/9/17 15:32
     **/
    public boolean leftPush(String key, Object... vals) {
        try {
            redisTemplate.opsForList().leftPushAll(key, vals);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 功能描述: redis的rpop命令，移除列表的最后一个元素，返回值为移除的元素，当列表不存在时，返回 null
     * 注意事项: 这个方法其实不是很友好，因为当返回值为null时，有两种可能：(1).这个key在redis中不存在, (2).发生了异常
     *          所以在实际的生产中，应该要考虑是否将异常抛出去或者用某种方式来区别异常和key在redis中不存在这两种情况
     * @param key 键
     * @return Object 键对应的值 或者 为null
     * @author moonlight
     * @date 2021/9/17 15:40
     **/
    public Object rightPop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能描述: {@link RedisHandler#rightPop(String)}的阻塞版本，
     *          移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param key 键
     * @param timeout 超时时间
     * @param timeUnit 超时时间单位
     * @return Object 键对应的值 或者 为null
     * @author moonlight
     * @date 2021/9/17 15:40
     **/
    public Object blockRightPop(String key, long timeout, TimeUnit timeUnit) {
        try {
            return redisTemplate.opsForList().rightPop(key, timeout, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}