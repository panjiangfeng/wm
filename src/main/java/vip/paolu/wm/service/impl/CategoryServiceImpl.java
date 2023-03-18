package vip.paolu.wm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.paolu.wm.common.CustomException;
import vip.paolu.wm.entity.Category;
import vip.paolu.wm.entity.Dish;
import vip.paolu.wm.entity.Setmeal;
import vip.paolu.wm.mapper.CategoryMapper;
import vip.paolu.wm.service.CategoryService;
import vip.paolu.wm.service.DishService;
import vip.paolu.wm.service.SetmealService;

/**
 * Description: 分类 包含菜品分类和套餐分类
 * User: Pan
 * Date: 2023-03-12-21:23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 移除分类,优先判断是否被菜系或套餐关联
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> queryDishWrapper = new LambdaQueryWrapper<>();
        queryDishWrapper.eq(Dish::getCategoryId, id);
        //dish跟类别关联的数量
        int relationCount1 = dishService.count(queryDishWrapper);
        LambdaQueryWrapper<Setmeal> querySetmealWrapper = new LambdaQueryWrapper<>();
        if (relationCount1 > 0) {
            throw new CustomException("当前分类关联了菜品,无法删除");
        }
        querySetmealWrapper.eq(Setmeal::getCategoryId, id);
        //dish跟类别关联的数量
        int relationCount2 = setmealService.count(querySetmealWrapper);
        if (relationCount2 > 0) {
            throw new CustomException("当前分类关联了套餐,无法删除");
        }
        super.removeById(id);
    }
}
