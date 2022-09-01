package com.zzj.rabbitmq.workqueues;

import com.rabbitmq.client.*;
import com.zzj.rabbitmq.workqueues.Publisher;
import com.zzj.rabbitmq.util.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @Date 2022/8/29 10:49
 * @author zhangzhongjie
 */
public class Consumer {

    @Test
    public void consume1() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建队列
        channel.queueDeclare(Publisher.QUEUE_NAME, false, false, false, null);

        //3.5设置消息的流控
        channel.basicQos(1);

        //4. 监听消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("消费者一号获取到消息：" + new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Publisher.QUEUE_NAME, false, callback);
        System.out.println("开始监听队列");

        System.in.read();
    }

    @Test
    public void consume2() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建队列
        channel.queueDeclare(Publisher.QUEUE_NAME, false, false, false, null);

        //3.5设置消息的流控
        channel.basicQos(1);

        //4. 监听消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("消费者二号获取到消息：" + new String(body, "UTF-8"));
                //消息标识和是否批量操作
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Publisher.QUEUE_NAME, false, callback);
        System.out.println("开始监听队列");

        System.in.read();
    }
}
