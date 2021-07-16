package com.jhmarryme.web.controller;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * 针对restfulAPI的测试用例
 *
 * @author JiaHao Wang
 * @date 2020/9/8 12:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @lombok.SneakyThrows
    @Test
    public void whenQuerySuccess() {
        // get请求, 参数为Json类型(UTF-8), 期望返回码为200, 返回一个集合, 长度为3
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
//                .param("username", "wjh")
//                .param("age", "10")
//                .param("ageTo", "11")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();

        System.out.println("result = " + result);
    }

    @SneakyThrows
    @Test
    public void whenGetInfoSuccess() {
        // get请求, 参数为Json类型(UTF-8), 期望返回码为200, 返回一个User对象, 期望username=wjh
        String result =
                mockMvc.perform(MockMvcRequestBuilders.get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$" +
                        ".username").value("wjh"))
                        .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @SneakyThrows
    @Test
    public void whenGetInfoFailed() {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    public void whenCreateSuccess() {

        String content = "{\n" +
                "  \"username\": \"wjh\",\n" +
                "  \"password\": \"\",\n" +
                "  \"birthday\":" + new Date().getTime() + "\n" +
                "}";
        String result =
                mockMvc.perform(MockMvcRequestBuilders.post("/user").content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                        .andReturn().getResponse().getContentAsString();

        System.out.println("result = " + result);
    }

    @SneakyThrows
    @Test
    public void whenUpdateSuccess() {
        Date date =
                new Date(LocalDateTime.now().plusYears(1L).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        //language=JSON
        String content = "{\n" +
                "  \"id\": 1,\n" +
                "  \"username\": \"wjh\",\n" +
                "  \"password\": \"\",\n" +
                "  \"birthday\":" + date.getTime() + "\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/user/1").content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @SneakyThrows
    @Test
    public void whenDeleteSuccess() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}