package vip.paolu.wm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import vip.paolu.wm.common.R;
import vip.paolu.wm.entity.Employee;
import vip.paolu.wm.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:admin
 * User: Pan
 * Date: 2023-03-11-16:25
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录账号
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //转换md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        //查询数据库中是否存在用户
        Employee tempEmployee = employeeService.getOne(queryWrapper);
        if (tempEmployee == null) {
            return R.error("该用户不存在!");
        }
        if (!password.equals(tempEmployee.getPassword())) {
            return R.error("密码输入错误!");
        }
        if (tempEmployee.getStatus() == 0) {
            return R.error("该用户已被ban");
        }
        request.getSession().setAttribute("employee", tempEmployee.getId());
        return R.success(tempEmployee);
    }

    /**
     * 退出账号
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 插入admin端用户
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        //赋密码初始值
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //获取当前登录的用户
        //Long creatorId = (Long) request.getSession().getAttribute("employee");
        //存入当前登录用户
        //employee.setCreateUser(creatorId);
        //employee.setUpdateUser(creatorId);
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        employeeService.save(employee);
        log.info("正在添加admin端用户{}", employee);
        return R.success("新增成功");
    }

    /**
     * 分页查询列表下所有用户
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> getEmployees(int page, int pageSize, String name) {
        Page pageInfo = new Page(page, pageSize);
        log.info("page:{}pageSize:{}name:{}", page, pageSize, name);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        queryWrapper.orderByAsc(Employee::getUpdateTime);
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 更新用户信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        //Long creatorId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateUser(creatorId);
        //employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("修改成功!");
    }

    /**
     * 通过id获取admin端用户
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("id:{}", id);
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有该用户!");
    }
}
