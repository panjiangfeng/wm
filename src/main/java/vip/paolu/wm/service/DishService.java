package vip.paolu.wm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.paolu.wm.dto.DishDto;
import vip.paolu.wm.entity.Dish;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-13-0:02
 */
public interface DishService extends IService<Dish> {
    void insertDish(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateDishWithFlavour(DishDto dishDto);
}
