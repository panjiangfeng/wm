package vip.paolu.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.paolu.wm.entity.User;
import vip.paolu.wm.mapper.UserMapper;
import vip.paolu.wm.service.UserService;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-17-0:13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
