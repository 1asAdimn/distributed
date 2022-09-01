package com.zzj.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zzj.rabbitmq.util.RabbitMQConnectionUtil;
import org.junit.Test;

/**
 * @author zzj
 * @description
 * @date 2022/8/28 22:54
 */
public class Publisher {

    public static final String QUEUE_NAME = "workQueues";

    @Test
    public void publish() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //4. 发布消息

        for (int i = 0; i < 10; i++) {
            String message = "Hello world !"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        }
        System.out.println("消息发送成功！");
    }

}
