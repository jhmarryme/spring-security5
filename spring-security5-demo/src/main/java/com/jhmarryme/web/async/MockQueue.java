package com.jhmarryme.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 模拟一个消息队列
 * @author JiaHao Wang
 * @date 2020/9/10 19:50
 */
@Component
public class MockQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockQueue.class);

    private String placeOrder;

    private String completeOrder;


    public String getPlaceOrder() {
        return placeOrder;
    }


    public MockQueue setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            // 监听到 有下单请求
            LOGGER.info("接到下单请求, " + placeOrder);

            // 模拟处理业务逻辑
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;

            LOGGER.info("下单请求处理完毕," + placeOrder);
            this.placeOrder = null;
        }).start();

        return this;
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public MockQueue setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
        return this;
    }
}
