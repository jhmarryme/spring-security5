package com.jhmarryme.web.controller;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * description:
 *
 * @author JiaHao Wang
 * @date 2020/9/10 18:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @SneakyThrows
    @Test
    public void whenUploadFileSuccess() {
        // MockMultipartFile: 文件映射名, 真实文件名, 上传类型, 文本内容
        String result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
                .file(new MockMultipartFile("file", "test.txt", "mutipart/form-data",
                        "hello world".getBytes(StandardCharsets.UTF_8))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println("result = " + result);
    }

}