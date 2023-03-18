package vip.paolu.wm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.paolu.wm.entity.Dish;

/**
 * Description:菜品
 * User: Pan
 * Date: 2023-03-12-23:59
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
