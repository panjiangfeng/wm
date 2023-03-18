package vip.paolu.wm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.paolu.wm.entity.Category;

/**
 * Description: 分类 包含菜品分类和套餐分类
 * User: Pan
 * Date: 2023-03-12-21:19
 */
public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
