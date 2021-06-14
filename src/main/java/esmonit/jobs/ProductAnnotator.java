package esmonit.jobs;

import esmonit.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

@Component
@Slf4j
public class ProductAnnotator {

    private ProductService productService;
    // 0 0 12 * * ?	 -> Fire at 12:00 PM (noon) every day
    @Scheduled( cron = "0/20 * * * * *" )
    public void work() {
        System.out.println();
        log.info("Firing a Job");
    }
}

