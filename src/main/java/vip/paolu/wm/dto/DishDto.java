package vip.paolu.wm.dto;


import lombok.Data;
import vip.paolu.wm.entity.Dish;
import vip.paolu.wm.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
