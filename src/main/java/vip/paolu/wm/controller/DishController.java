package vip.paolu.wm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.paolu.wm.common.R;
import vip.paolu.wm.dto.DishDto;
import vip.paolu.wm.entity.Category;
import vip.paolu.wm.entity.Dish;
import vip.paolu.wm.entity.DishFlavor;
import vip.paolu.wm.service.CategoryService;
import vip.paolu.wm.service.DishFlavorService;
import vip.paolu.wm.service.DishService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-12-23:57
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> insertDish(@RequestBody DishDto dishDto) {
        dishService.insertDish(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 分页获取菜品
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> getDish(int page, int pageSize, String name) {
        log.info("菜品套餐");
        //先获取菜品列表
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, queryWrapper);
        //创建dto并拷贝records
        Page<DishDto> dishDtoPage = new Page<DishDto>();
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        //遍历records并添加到类别名
        List<DishDto> list = records.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Category category = categoryService.getById(item.getCategoryId());
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());
        //添加记录到类别中
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 通过分类id获取菜品包括口味
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDishById(@PathVariable Long id) {
        DishDto dish = dishService.getByIdWithFlavor(id);
        return R.success(dish);
    }

    /**
     * 更新菜品联动口味
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto) {
        dishService.updateDishWithFlavour(dishDto);
        return R.success("修改菜品成功");
    }


    //@GetMapping("/list")
    //public R<List<Dish>> getDishById(Dish dish) {
    //    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    //    queryWrapper.eq(Dish::getCategoryId, dish.getCategoryId()).eq(Dish::getStatus, 1);
    //    queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
    //    List<Dish> list = dishService.list(queryWrapper);
    //    return R.success(list);
    //}

    /**
     * 根据分类id查询菜品
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> getDishById(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, dish.getCategoryId()).eq(Dish::getStatus, 1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        List<DishDto> dishDtos = list.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            //注入分类名
            Long categoryId = item.getCategoryId();
            LambdaQueryWrapper<Category> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Category::getId, categoryId);
            Category category = categoryService.getOne(queryWrapper1);
            dishDto.setCategoryName(category.getName());
            //注入口味
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> flavors = dishFlavorService.list(queryWrapper2);
            dishDto.setFlavors(flavors);
            return dishDto;

        }).collect(Collectors.toList());
        return R.success(dishDtos);
    }
}
