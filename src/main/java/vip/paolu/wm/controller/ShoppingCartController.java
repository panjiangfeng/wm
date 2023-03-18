package vip.paolu.wm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.paolu.wm.common.BaseContext;
import vip.paolu.wm.common.R;
import vip.paolu.wm.entity.ShoppingCart;
import vip.paolu.wm.service.ShoppingCartService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-18-0:06
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> addCart(@RequestBody ShoppingCart shoppingCart) {
        log.info("addcart:{}", shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        if (cart != null) {
            Integer number = cart.getNumber();
            cart.setNumber(number + 1);
            shoppingCartService.updateById(cart);
        } else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cart = shoppingCart;
        }
        return R.success(cart);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> getShoppingCart() {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        List<ShoppingCart> carts = shoppingCartService.list(queryWrapper);
        return R.success(carts);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> subShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (shoppingCart.getSetmealId() != null) {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
            ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
            if (cart.getNumber() == 1) {
                shoppingCartService.remove(queryWrapper);
                return R.success(cart);
            } else {
                shoppingCart.setNumber(cart.getNumber() - 1);
                log.info("Shopping Cart{}", cart);
                shoppingCartService.updateById(cart);
                return R.success(cart);
            }
        } else if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
            ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
            if (cart.getNumber() == 1) {
                shoppingCartService.remove(queryWrapper);
                return R.success(cart);
            } else {
                cart.setNumber(cart.getNumber() - 1);
                log.info("Shopping Cart{}", cart);
                shoppingCartService.updateById(cart);
                return R.success(cart);
            }
        }
        return R.error("删除失败");
    }

    @DeleteMapping("/clean")
    public R<String> removeUserCart() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppingCartService.remove(queryWrapper);

        return R.success("清空购物车成功");
    }
}
