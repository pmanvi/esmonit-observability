package esmonit.clientsdk;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class EsMonitServices {

        private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("EsMonitSuit");
        private final RateLimiter rateLimiter = RateLimiter.ofDefaults("EsMonitSuit");

        private final static long TIMEOUT = 3000; // ms
        private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
        private Retrofit retrofit = null;

        private EsMonitServices() {
                retrofit  = new Retrofit.Builder()
                        .baseUrl(/*baseUrl*/"http://localhost:9000")
                        .client(okHttpClient)
                        //.addCallAdapterFactory(CircuitBreakerCallAdapter.of(circuitBreaker))
                        //.addCallAdapterFactory(RateLimiterCallAdapter.of(rateLimiter))
                        .addConverterFactory(GsonConverterFactory.create()).build();
                /**
                 * Rate Limiter
                 * If the number of calls are exceeded within the period defined by the RateLimiter,
                 * a HTTP 429 response (too many requests) will be returned.
                 */

        }
        private  <S> S createService(Class<S> serviceClass) {
            return retrofit.create(serviceClass);
        }
        private static final EsMonitServices  factory = new EsMonitServices();
        public static EsMonitServices get() {
                return factory;
        }

        public static ProductService product() {
                return EsMonitServices.get().createService(ProductService.class);
        }
}
