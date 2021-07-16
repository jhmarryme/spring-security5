package com.jhmarryme.web.controller;

import com.jhmarryme.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * description:
 *
 * @author JiaHao Wang
 * @date 2020/9/10 18:23
 */
@RestController
@RequestMapping("/file")
public class FileController {

    // 文件存放路径为当前路径
    private String folder = "D:\\com.jhmarryme\\JavaBasicLearning\\technicalPractice\\security\\security-demo" +
            "\\src\\main\\java\\com\\imooc\\web\\controller";

    /**
     *  简单的文件上传
     *
     * @Param: [file]
     * @Return: com.imooc.dto.FileInfo
     * @author JiaHao Wang
     * @Since: 2020/9/10 18:28
     * @Throws
     **/
    @PostMapping
    public FileInfo upload(MultipartFile file) throws Exception {

        /*
        org.springframework.mock.web.MockMultipartFile@4fe2dd02[
          name=file
          originalFilename=test.txt
          contentType=mutipart/form-data
          content={104,101,108,108,111,32,119,111,114,108,100}
        ]
         */
        System.out.println(ReflectionToStringBuilder.toString(file, ToStringStyle.MULTI_LINE_STYLE));


        File localFile = new File(folder, System.currentTimeMillis() + ".txt");

        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }


    /**
     *  简单的文件下载
     *
     * @Param: [id, request, response]
     * @Return: void
     * @author JiaHao Wang
     * @Since: 2020/9/10 19:04
     * @Throws
     **/
    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
             OutputStream outputStream = response.getOutputStream();) {

            // 下载
            response.setContentType("application/x-download");

            // 定义下载时的文件名
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }

    }
}
