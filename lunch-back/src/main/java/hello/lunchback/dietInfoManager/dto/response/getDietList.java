package hello.lunchback.dietInfoManager.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class getDietList {

    List<Diet> dietList = new ArrayList<>();

}
