package vip.paolu.wm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.paolu.wm.common.CustomException;
import vip.paolu.wm.common.R;
import vip.paolu.wm.entity.Category;
import vip.paolu.wm.service.CategoryService;

import java.util.List;

/**
 * Description:分类 包含菜品分类和套餐分类
 * User: Pan
 * Date: 2023-03-12-21:18
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询所有类别
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> getAllCategories(int page, int pageSize) {
        log.info("查询类别");
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @GetMapping("list")
    public R<List<Category>> getAllCategorys(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> categorys = categoryService.list(queryWrapper);
        return R.success(categorys);
    }

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping()
    public R<String> addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    @DeleteMapping
    public R<String> removeCategory(Long ids) {
        log.info("id:{}", ids);

        try {
            categoryService.remove(ids);
        } catch (CustomException e) {
            return R.error(e.getMessage());
        }
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> updateCategory(@RequestBody Category category) {

        categoryService.updateById(category);
        return R.success("修改分类成功");

    }


}
