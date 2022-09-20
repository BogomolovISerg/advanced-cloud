package test.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import test.test.api.ProductApi;

import java.util.Arrays;

@RestController
@RequestMapping("/test")
public class TestKafka {

    @Qualifier(value = "KafkaTest")
    @Autowired
    private KafkaTemplate<Long, UserDto> kafkaTemplate;

    @Autowired
    private ProductApi productApi;

    @PostMapping
    public void sendOrder(){
 //       kafkaTemplate.send("GeekTopicForUsers", 1L, new UserDto("Bob", "Parker", 35));
        ListenableFuture<SendResult<Long, UserDto>> future = kafkaTemplate.send("Neostudy", new UserDto("Bob", "Parker", 35));
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }

    @GetMapping
    public void testFeign() {
        System.out.println(Arrays.asList(productApi.getAllProducts()));
    }

    @GetMapping("/{id}")
    public void testFeign(@PathVariable Long id) {
        System.out.println(Arrays.asList(productApi.getProductPyId(id)));
    }
}
