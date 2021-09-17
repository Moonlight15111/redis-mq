package org.moonlight.redismq.controller;

import org.moonlight.redismq.handler.RedisHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 *   测试 - 消费者controller,消息的消费方
 * 主要逻辑:
 *   根据接口传入的key去redis中消费对应的消息
 * 注意事项:
 *
 * @author Moonlight
 * @date 2021-09-17 16:02
 */
@RestController
@RequestMapping("/cons")
public class ConsumerController {

    private final RedisHandler redisHandler;

    @Autowired
    public ConsumerController(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }

    @GetMapping("/block/{key}")
    public String block(@PathVariable(name = "key") String key) {
        return String.valueOf(redisHandler.blockRightPop(key, 10, TimeUnit.SECONDS));
    }

    @GetMapping("/nonblock/{key}")
    public String nonblock(@PathVariable(name = "key") String key) {
        return String.valueOf(redisHandler.rightPop(key));
    }

}