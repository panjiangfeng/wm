package vip.paolu.wm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.paolu.wm.entity.Employee;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-11-16:19
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
