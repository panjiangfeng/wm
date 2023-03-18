package vip.paolu.wm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Description: 入口
 * User: Pan
 * Date: 2023-03-11-15:26
 */
@Slf4j
@ServletComponentScan
@SpringBootApplication
public class WmApplication {
    public static void main(String[] args) {
        SpringApplication.run(WmApplication.class, args);
        log.info("项目启动成功");
    }
}
