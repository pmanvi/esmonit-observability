package esmonit.security;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Mapped Diagnostic Context (MDC) is used to enhance
 * the application logging by adding some meaningful information to log entries.
 */
@Component
@Slf4j
public class ContextLoggerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        String token = request.getHeader("Authorization");
        log.info("Token : {} ", token);
        // validate token, extract apiKey
        String apiKey = "1232";
        MDC.put("apiKey", apiKey);
        ApiContext context = new ApiContext(apiKey,"praveen");
        try {
              filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Failed to set API Context in log from header ", e);
        } catch (Exception catchAll){
            logger.error(catchAll);
        }
        finally {
            MDC.remove("apiKey");
        }
    }
}
