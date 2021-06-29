package esmonit.product.dao;

import esmonit.product.domain.Product;
import esmonit.security.ApiContext;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;

@Repository
@Slf4j
public class ProductRepository {

    @Autowired
    JdbcTemplate template;
    @Autowired
    ApiContext apiContext;

    public List<Product> getAllProducts(){
        List<Product> products = template.query("select id, name,category from product",(result, rowNum)->new Product(result.getInt("id"),
                result.getString("name"),result.getString("category")));
        return products;
    }
    public Product getProduct(Integer productId){
        String query = "select * from product where id=?";
        Product product = template.queryForObject(query,new Object[]{productId},new BeanPropertyRowMapper<>(Product.class));

        return product;
    }
    /*Adding an product into database table*/
    public Integer addProduct(Integer id, String name, String category){
        String query = "insert into product values(?,?,?)";
        log.info("Query {} ",query);
        return template.update(query,id,name,category);
    }
    public Integer create(Product product) {
        log.info("Creating Product {} ",product);
        return addProduct(product.getId(),product.getName(),product.getCategory());
    }


    public Integer delete(Integer id){
        String query = "delete from product where id =?";
        return template.update(query,id);
    }
    public Long getTotalProducts() {
        if(false) {
            //return getAllProducts().stream().filter(this::countable).count();
            return new Long(getAllProducts().size());
        }
        return template.queryForObject("select count(*) from product",Long.class);
    }

    private boolean countable(Product product) {

        try{
            log.info("Finding out product if its coutable  {}",product);
            // Lets cheat
            Thread.sleep(100);
            log.info("Finding out product if its coutable  {}",product);
        } catch (Exception e) {}
        return true;
    }

    private boolean countableWithCircuitBreaker(Product p) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(5)
                .slowCallDurationThreshold(Duration.of(1, SECONDS))
                .build();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker circuitBreaker =
                            registry.circuitBreaker("countableFunction");
        Function<Product, Boolean> decorated = CircuitBreaker
                .decorateFunction(circuitBreaker, this::countable);
        return  decorated.apply(p);
    }

    public List<Integer> bulkInsert(final List<Product> products) {
        List<Integer> retIds = new ArrayList<>();
        for(Product p:products) {
           retIds.add(this.create(p));
        }
        return retIds;
    }

    public int[] batchInsert(final List<Product> products) {
        if(products.size() > 50) {
            // manage the batch sizes
        }

        String insertSql = "insert into product values(?,?,?)";
        int[] productIds = template.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pStmt, int j) throws SQLException {
                Product product = products.get(j);
                //id, name,category
                pStmt.setInt(1, product.getId());
                pStmt.setString(2, product.getName());
                pStmt.setString(3, product.getCategory());
            }
            @Override
            public int getBatchSize() {
                return products.size();
            }
        });
        log.info("Created products with following Ids '{}'", Arrays.asList(productIds));
        return productIds;
    }

}
