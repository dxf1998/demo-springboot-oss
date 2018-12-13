package com.dxf.demospringbootoss.controller;

import com.dxf.demospringbootoss.uitl.AliyunOSSUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @PackageName:com.dxf.demospringbootoss.controller
 * @Auther: 独善其身的狗
 * @Data: 2018/12/13 0013 上午 11:46
 */
@Controller
public class UpLoadController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TO_PATH="upLoad";
    private static final String RETURN_PATH="success";

    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;
    @RequestMapping("/toUpLoadFile")
    public String toUpLoadFile(){
        return TO_PATH;
    }

    /** 文件上传*/
    @RequestMapping(value = "/uploadFile")
    public String uploadBlog(@RequestParam("file") MultipartFile file) {
        logger.info("文件上传");
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        try {

            if (file!=null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    String uploadUrl = aliyunOSSUtil.upLoad(newFile);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return RETURN_PATH;
    }
}
