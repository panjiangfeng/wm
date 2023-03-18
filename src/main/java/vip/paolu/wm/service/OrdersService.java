package vip.paolu.wm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.paolu.wm.entity.Orders;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-18-10:46
 */

public interface OrdersService extends IService<Orders> {

    void insertOrder(Orders orders);
}
