package com.jhmarryme.web.service;

import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author JiaHao Wang
 * @date 2020/9/9 18:16
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void greeting(String name) {

        System.out.println("greeting " + name);
    }
}
