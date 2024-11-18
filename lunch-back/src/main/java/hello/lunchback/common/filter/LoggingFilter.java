package hello.lunchback.common.filter;

import hello.lunchback.common.entity.LoggingEntity;
import hello.lunchback.common.repository.LoggingRepository;
import jakarta.servlet.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
@Component
@Order(1)
public class LoggingFilter implements Filter {

    private final FilterManager filterManager;
    private final LoggingRepository loggingRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        LoggingEntity loggingEntity = filterManager.info(servletRequest);
        filterChain.doFilter(servletRequest,servletResponse);
        long completeTime = System.currentTimeMillis() - start;
        loggingEntity.setCompleteTime(String.valueOf(completeTime));
        printLog(loggingEntity);
        loggingRepository.save(loggingEntity);

    }

    private static void printLog(LoggingEntity loggingEntity) {
        log.info("ClientIp : {}, Time : {}, RequestApi : {}, Method : {} CompleteTime : {}",
                loggingEntity.getClientIp(), loggingEntity.getDate(),
                loggingEntity.getRequestApi(), loggingEntity.getMethod(),
                loggingEntity.getCompleteTime());
    }

}
