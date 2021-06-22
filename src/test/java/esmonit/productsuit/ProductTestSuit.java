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

import static org.junit.jupiter.api.Assertions.*;

public class ProductTestSuit {

    ProductService service =  EsMonitServices.product();

    AtomicInteger counter = new AtomicInteger(1);

    @RepeatedTest(10)
    public void createProducts() throws IOException {
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
        counter.incrementAndGet();
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
                .someRandomProducts(startCounter.intValue()+1)
                .stream().map(service::create)
                    .parallel()
                    .forEach(c-> {
                        try {
                          //System.out.println("Executing"+c.request().toString());
                          Integer id = c.execute().body();
                          assertNotNull(id);
                        }catch (IOException e) {
                            fail(e);
                        }
                    });
    }
}
