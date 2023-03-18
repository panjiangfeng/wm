package vip.paolu.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.paolu.wm.entity.ShoppingCart;
import vip.paolu.wm.mapper.ShoppingCartMapper;
import vip.paolu.wm.service.ShoppingCartService;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-18-0:09
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
