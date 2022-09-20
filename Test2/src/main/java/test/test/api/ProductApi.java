package test.test.api;

import com.geekbrains.spring.web.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import test.test.MyConf;

import java.util.List;

@FeignClient(value = "Products", url = "http://localhost:8189/web-market-core/api/v1/products",
    configuration = MyConf.class)
public interface ProductApi {

    @RequestMapping(method = RequestMethod.GET, value = "")
    List<ProductDto> getAllProducts();

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ProductDto getProductPyId(@PathVariable Long id);

}
