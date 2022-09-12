package com.geekbrains.test1;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer {

    @KafkaListener(topics = "GeekTopicForUsers")
    public void msgListener(UserDto user){
        System.out.println(user);
    }

}
