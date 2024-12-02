package hello.lunchback.alarmManagement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WebSocketController {


    @MessageMapping("/{memberId}")// 여기로 전송되면 메서드 호출, WebSocketConfig prefixes 에서 적용한건 생략
    @SendTo("/{memberId}")
    public String chat(@DestinationVariable Integer memberId, String message){
        return message;
    }
}
