package vip.paolu.wm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.paolu.wm.common.R;
import vip.paolu.wm.entity.Orders;
import vip.paolu.wm.service.OrdersService;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-18-10:45
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Autowired
    private OrdersService ordersService;


    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据：{}", orders);
        ordersService.insertOrder(orders);
        return R.success("下单成功");
    }
}
