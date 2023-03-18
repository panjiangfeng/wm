package vip.paolu.wm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vip.paolu.wm.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * Description: 文件上传
 * User: Pan
 * Date: 2023-03-14-23:51
 */
@Slf4j
@RequestMapping("/common")
@RestController
public class CommonController {
    @Value("${rjwm.path}")
    private String SAVE_PATH; //存放临时路径

    /**
     * 上传文件功能
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> uploadFile(MultipartFile file) {
        log.info("file:{}", file);

        File imgDir = new File(SAVE_PATH);
        if (!imgDir.exists()) {
            imgDir.mkdir();
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        try {
            file.transferTo(new File(SAVE_PATH + fileName));
        } catch (IOException e) {
            return R.error(e.getMessage());
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        File file = new File(SAVE_PATH + name);
        try {
            FileInputStream in = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = in.read(bytes)) != -1) {
                out.write(bytes, 0, length);
                out.flush();
            }
            //TODO:此处bug  如果报错连接就关闭不了
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            //TODO:后续维护
        } catch (IOException e) {
            //TODO:后续维护
        }


    }
}
