package esmonit;

import esmonit.security.ApiContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import co.elastic.apm.opentracing.ElasticApmTracer;
import io.opentracing.Tracer;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

@SpringBootApplication
@Configuration
@Slf4j
public class EsMonit {

	public static void main(String[] args) {
		SpringApplication.run(EsMonit.class, args);
	}

	@Bean public Tracer tracer() {
		return new ElasticApmTracer();
	}
	/**
	 * -Dio.opentracing.contrib.jdbc.slowQueryThresholdMs=10
	 * -javaagent:/Users/pmanvi/AOS/aos-tenant-onboard/build/libs/elastic-apm-agent-1.22.0.jar
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

}

/**
 * Mapped Diagnostic Context (MDC) is used to enhance
 * the application logging by adding some meaningful information to log entries.
 */
@Component
class ContextLoggerFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) {
		MDC.put("apiKey", request.getHeader("apiKey"));
		try {
			filterChain.doFilter(request, response);
		} catch (ServletException | IOException e) {
			logger.error("Failed to set API Context in log from header ",e);
		} finally {
			MDC.remove("apiKey");
		}
	}
}
