package org.moonlight.redismq.stream.controller;

import org.moonlight.redismq.stream.StreamConsumerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/stream")
public class StreamController {

    private final StreamConsumerManager streamConsumerManager;

    @Autowired
    public StreamController(StreamConsumerManager streamConsumerManager) {
        this.streamConsumerManager = streamConsumerManager;
    }

    @GetMapping("/product/{key}")
    public String production(@PathVariable(name = "key") String key) {
       streamConsumerManager.product(key);
       return " stream  " + key + " product success ";
    }

    @GetMapping("/consumer/{key}")
    public String consumer(@PathVariable(name = "key") String key) {
        streamConsumerManager.createConsumer(key);
        return " stream " + key + " create consumer success ";
    }

}