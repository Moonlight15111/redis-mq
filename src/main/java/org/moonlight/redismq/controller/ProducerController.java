package org.moonlight.redismq.controller;

import org.moonlight.redismq.handler.RedisHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *   测试 - 生产者controller,消息的生产方
 * 主要逻辑:
 *   接收接口传入的数据，存入redis
 * 注意事项:
 *
 * @author Moonlight
 * @date 2021-09-17 16:02
 */
@RestController
@RequestMapping("/prod")
public class ProducerController {

    private final RedisHandler redisHandler;

    @Autowired
    public ProducerController(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }

    @GetMapping("/{key}")
    public String production(@PathVariable(name = "key") String key) {
        return String.valueOf(redisHandler.leftPush(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    }

}