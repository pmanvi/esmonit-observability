package esmonit.endpoints;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomePageApi {

    @Autowired
    JdbcTemplate template;
    @Autowired
    Tracer tracer;

    @GetMapping("/home")
    public String home() {
        log.info("opentracing back is being called");
        Span span = tracer.buildSpan("home").start();
        span.log("Constructing part1");
        String part1 = part1();
        span.log("Constructed part1");
        span.log("Constructing part2");
        String part2 = part2();
        span.log("Constructing part2");
        span.finish();
        return "Application is up and running Part1:"+part1+"  Part2:"+part2;
    }

    private String part1() {
        return "part1";
    }
    private String part2() {
        return "part2";
    }


    @GetMapping("/")
    public String index() {
        log.info("opentracing back is being called");
        return "Application is up and running";
    }

}
