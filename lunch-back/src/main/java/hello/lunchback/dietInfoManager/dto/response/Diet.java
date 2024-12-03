package hello.lunchback.dietInfoManager.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Diet {

    private LocalDate date;
    private List<MenuItem> menuItems = new ArrayList<>();


    public void setDate(String orderDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate parse = LocalDate.parse(orderDate, formatter);
        this.date = parse;
    }
}
