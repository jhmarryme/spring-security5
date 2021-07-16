package com.jhmarryme.web.async;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author JiaHao Wang
 * @date 2020/9/10 19:55
 */
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueListener.class);

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        new Thread(() -> {
            while (true) {
                // 监听到 下单请求处理完成
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String orderNumber = mockQueue.getCompleteOrder();
                    LOGGER.info("返回订单处理结果:"+orderNumber);
                    // 整个异步处理结束, 准备向浏览器返回结果
                    deferredResultHolder.getMap().get(orderNumber).setResult("place order success");

                    // 清空消息
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
