package test.test;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer {

    @KafkaListener(topics = "Neostudy")
    public void msgListener(UserDto user){
        System.out.println(user);
    }




}
