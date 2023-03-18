package vip.paolu.wm.dto;


import lombok.Data;
import vip.paolu.wm.entity.Setmeal;
import vip.paolu.wm.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
