package esmonit.product.service;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.mongodb.client.MongoClient;
import esmonit.product.dao.ProductRepository;
import esmonit.product.domain.Product;
import esmonit.product.domain.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.executor.RedissonClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

// Combined for brevity, we don't have delegation in java :( like in awesome Kotlin
@Component
@Slf4j
public class ProductService extends ProductRepository implements ProductDao {

    @Autowired
    private RedissonClient redisClient;
    @Autowired
    private MongoClient mongoClient;

    public ProductType getProductType(int productId) {
        // Lengthy algorithm to cacludate one.

        return ProductType.Premium;
    }

    @Override
    public Integer create(Product product) {
        log.info("Creating Product {} ",product);
        //trackRestTemplate();
        int id = super.create(product);
        trackRestTemplate();
        return id;
    }
    private void trackRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:9000/products";
        ResponseEntity<Integer> response
                = restTemplate.getForEntity(fooResourceUrl + "/_count", Integer.class);

        log.info(" Response {}",response.getBody());
        for(int i=0; i< 2; i++) {
            ResponseEntity<Product> res = restTemplate.getForEntity(fooResourceUrl+"/"+(i+1), Product.class);
            log.info("Product = {} ",res.getBody());
        }
        Map<String,Integer> map = redisClient.getMap("Tracker");
        map.put("currentCount", response.getBody());
        mongoClient.listDatabaseNames().forEach(
                name -> System.out.println("name = " + name)
        );  
    }

}
