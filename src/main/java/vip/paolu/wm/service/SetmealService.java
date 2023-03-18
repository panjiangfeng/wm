package vip.paolu.wm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.paolu.wm.dto.SetmealDto;
import vip.paolu.wm.entity.Setmeal;

import java.util.List;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-13-0:02
 */
public interface SetmealService extends IService<Setmeal> {
    void insertSetmeal(SetmealDto setmealDto);

    SetmealDto getSetmealById(Long id);

    void deleteSetmealWithDish(List<Long> ids);

    void updateSetmealWithDish(SetmealDto setmealDto);
}