package vip.paolu.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.paolu.wm.entity.Employee;
import vip.paolu.wm.mapper.EmployeeMapper;
import vip.paolu.wm.service.EmployeeService;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-11-16:22
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    
}
