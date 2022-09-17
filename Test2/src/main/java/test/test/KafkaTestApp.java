package test.test;

import com.geekbrains.spring.web.dto.ProductDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import test.test.api.ProductApi;

@SpringBootApplication
@EnableKafka
@EnableFeignClients
public class KafkaTestApp {

    // 1) Заменить RestTemplate на Feign клиент.
    // 2) Отпилить OrderService, привести работу с кафкой в пример с урока.
        // т.е. стучаться мы будем в карт сервис, а ОрдерСервис будет только обрабатывать из очереди
        // карт сервис, естественно, выступает в кач-ве продюсера
    // *Отпилить продукты в новый МС

    //ToDo подключим PayPal, соберем всё на докере, поработаем со Swagger

    private static ProductApi productApi;

    public static void main(String[] args) {
        SpringApplication.run(KafkaTestApp.class, args);

        ProductDto productDto = productApi.getProductPyId(13l);
    }
}
