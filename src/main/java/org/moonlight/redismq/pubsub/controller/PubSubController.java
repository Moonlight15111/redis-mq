package org.moonlight.redismq.pubsub.controller;

import org.moonlight.redismq.pubsub.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述:
 * <p>
 * 主要逻辑:
 * <p>
 * 注意事项:
 *
 * @author Moonlight
 * @date 2021-09-22 15:20
 */
@RestController
@RequestMapping("/pubsub")
public class PubSubController {

    private final Producer producer;

    @Autowired
    public PubSubController(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("/pub/{topic}")
    public String pub(@PathVariable(name = "topic") String topic) {
        producer.publish(topic);
        return " pub " + topic + " success";
    }

}