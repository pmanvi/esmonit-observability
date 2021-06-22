package esmonit.jobs;

import esmonit.product.service.ProductService;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

@Component
@Slf4j
public class ProductAnnotator {

    @Autowired
    private Tracer tracer;
    @Autowired
    private RedissonClient redisson;

    private ProductService productService;
    // 0 0 12 * * ?	 -> Fire at 12:00 PM (noon) every day
    @Scheduled( cron = "0/20 * * * * *" )
    public void work() {
        RMap map =  redisson.getMap("ProductAnnotator");

        map.put("Job", "job");
        log.info("Firing a Job");

        map.put("Job", 1);
        map.put("b", 2);
        map.put("c", 3);
        log.info("Job Completed");

    }
}

