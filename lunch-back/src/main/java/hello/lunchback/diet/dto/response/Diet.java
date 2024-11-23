package hello.lunchback.diet.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Diet {

    private String date;
    private List<MenuItem> menuItems = new ArrayList<>();

}
