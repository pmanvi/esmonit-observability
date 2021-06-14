package esmonit.endpoints;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import java.util.concurrent.CompletableFuture;


public class Circuitbreakers {

    @Bulkhead(name = "EsMonit", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<String> doSomethingAsync() throws InterruptedException {
        Thread.sleep(500);
        return CompletableFuture.completedFuture("Test");
    }
    /**
     * - resilience4j.retry.retryAspectOrder
     * - resilience4j.circuitbreaker.circuitBreakerAspectOrder
     * - resilience4j.ratelimiter.rateLimiterAspectOrder
     * - resilience4j.timelimiter.timeLimiterAspectOrder
     */
}
