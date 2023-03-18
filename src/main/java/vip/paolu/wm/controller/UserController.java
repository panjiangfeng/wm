package vip.paolu.wm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.paolu.wm.common.BaseContext;
import vip.paolu.wm.common.R;
import vip.paolu.wm.entity.User;
import vip.paolu.wm.service.UserService;
import vip.paolu.wm.utils.ValidateCodeUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Description: 用户业务层
 * User: Pan
 * Date: 2023-03-17-0:10
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     *
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code:{}", code);
            //TODO:此处发送短信可能出错
            //SMSUtils.sendMessage("跑路云", "", phone, code);
            session.setAttribute(phone, code);
            return R.success("发送验证码成功!");
        }
        return R.success("发送验证码失败!");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> map, HttpSession session) {
        String code = map.get("code");
        String phone = map.get("phone");
        String cacheCode = session.getAttribute(phone).toString();
        //TODO:项目以后要删除8898这项条件  写只是为了方便登录
        if (cacheCode != null && (code.equals("8898") || cacheCode.equals(code))) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }

    @PostMapping("/loginout")
    public R<String> logout(HttpSession session) {
        Long currentId = BaseContext.getCurrentId();
        session.removeAttribute("user");
        return R.success("退出成功");
    }
}
