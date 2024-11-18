package hello.lunchback.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "logging")
@Getter
@NoArgsConstructor
public class LoggingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    private String clientIp;
    private String date;
    private String requestApi;
    private String method;
    private String completeTime;

    public LoggingEntity(String clientIp, String time, String requestURI, String method) {
        this.clientIp = clientIp;
        this.date = time;
        this.requestApi = requestURI;
        this.method = method;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }
}
