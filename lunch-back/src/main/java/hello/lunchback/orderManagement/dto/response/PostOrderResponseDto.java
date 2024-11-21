package hello.lunchback.orderManagement.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostOrderResponseDto {

    private String code;

    public PostOrderResponseDto(String code) {
        this.code = code;
    }


    public static PostOrderResponseDto notExistedUser() {
        PostOrderResponseDto result = new PostOrderResponseDto("NU");
        return result;
    }

    public static PostOrderResponseDto databaseErr() {
        PostOrderResponseDto result = new PostOrderResponseDto("DBE");
        return result;
    }

    public static PostOrderResponseDto success() {
        PostOrderResponseDto result = new PostOrderResponseDto("SU");
        return result;
    }
}
