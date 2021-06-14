package esmonit.productsuit;


import esmonit.clientsdk.EsMonitServices;
import esmonit.clientsdk.ProductService;
import esmonit.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTestSuit {

    ProductService service =  EsMonitServices.product();

    @RepeatedTest(10)
    public void createProducts() throws IOException {
        int initialCount = service.count().execute().body().intValue();
        AtomicInteger counter = new AtomicInteger(initialCount);
        Product p = new Product();
        p.setId(counter.incrementAndGet());
        p.setName("Effective Kotlin ("+counter.get()+")");
        p.setCategory("Book");
        System.out.println("p = " + p);
        assertTrue(service.create(p).execute().body() > 0);
        System.out.println("service.getProduct(counter.get()) = "
                    + service.getProduct(counter.get()).execute().body());
    }
    @BeforeEach
    public void testCountLatency() throws IOException {
        long start = System.currentTimeMillis();
        long count = service.count().execute().body().intValue();
        long latency = System.currentTimeMillis() - start;
        System.out.println(count+" -> "+latency);
    }

    @Test
    public void testMonit() {

    }
    @Test
    public void testBulkInserts() throws IOException {
        Long startCounter =  service.count().execute().body();

        FakeProductsFactory.get()
                .someRandomProducts(startCounter.intValue())
                .stream().map(service::create)
                    //.parallel()
                    .forEach(c-> {
                        try {
                          Integer id = c.execute().body();
                          System.out.println("retId = " + String.valueOf(id));
                        }catch (IOException e) {

                        }
                    });
    }
}
