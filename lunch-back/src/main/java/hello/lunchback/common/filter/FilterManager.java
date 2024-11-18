package hello.lunchback.common.filter;

import hello.lunchback.common.entity.LoggingEntity;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class FilterManager {


    public LoggingEntity info(ServletRequest request) {
      // 로그 찍을 거 1.누가 2.몇시에 3.요청 API, 4. 처리시간

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String clientIp = httpRequest.getRemoteAddr();
        String time = time();
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

         return new LoggingEntity(clientIp, time, requestURI, method);

    }

    private String time() {
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return simpleDateFormat.format(now);

    }
}
