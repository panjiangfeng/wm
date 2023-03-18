package vip.paolu.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.paolu.wm.entity.OrderDetail;
import vip.paolu.wm.mapper.OrderDetailMapper;
import vip.paolu.wm.service.OrderDetailService;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-18-10:49
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
