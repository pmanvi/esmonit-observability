package esmonit;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import esmonit.security.ApiContext;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import co.elastic.apm.opentracing.ElasticApmTracer;
import io.opentracing.Tracer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

@SpringBootApplication
@Configuration
@Slf4j
//@org.springframework.cloud.netflix.eureka.EnableEurekaClient
public class EsMonit {

	public static void main(String[] args) {
		SpringApplication.run(EsMonit.class, args);
	}


	// TODO : ✅
	@Bean public Tracer tracer() {
		return new ElasticApmTracer();
	}
	/**
	 * -Dio.opentracing.contrib.jdbc.slowQueryThresholdMs=10
	 * -javaagent:/Users/pmanvi/IdeaProjects/esmonit-observability/elastic-apm-agent-1.24.0.jar
	 * -Delastic.apm.service_name=EsMonit
	 * -Delastic.apm.urls=http://localhost:8200
	 * -Delastic.apm.secret_token=
	 * -Delastic.apm.environment=arch
	 * -Delastic.apm.application_packages=esmonit
	 */
	@Bean
	public ApiContext getApiContext() {
		String apiKey = UUID.randomUUID().toString();
		// read from header
		MDC.put("apiKey", apiKey);
		return new ApiContext(apiKey,"praveen");
	}

	@Bean
	public RedissonClient redisClient() {
		return Redisson.create();
	}

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create();
	}

	/**
	 * JDBC
	 * SLOW Queries
	 * Pass the a threshold
	 * E.g. -Dio.opentracing.contrib.jdbc.slowQueryThresholdMs=100

	 * Fast Query
	 * Spans that complete faster than the optional excludeFastQueryThresholdMs flag will be not be reported. excludeFastQueryThresholdMs defaults to 0 which means disabled, can be enabled in two ways:
	 *
	 * Passing system property, E.g. -Dio.opentracing.contrib.jdbc.excludeFastQueryThresholdMs=100
	 */
	@Bean
	public String getConfigDisplay() {
		io.opentracing.contrib.jdbc.TracingDriver.setTraceEnabled(true);
		return "done";
	}

}

