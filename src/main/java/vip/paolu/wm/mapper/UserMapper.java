package vip.paolu.wm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.paolu.wm.entity.User;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-17-0:12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
