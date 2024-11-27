package hello.lunchback.dietInfoManager.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class getDietList {

    List<Diet> dietList = new ArrayList<>();

}
