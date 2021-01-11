package com.kang.io.netty.dubborpc.consumer;

import com.kang.io.netty.dubborpc.api.HelloService;
import com.kang.io.netty.dubborpc.netty.NettyClient;

/**
 * @author klr
 * @create 2021-01-11-22:44
 */
public class ClientBootstrap {

    //定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {

        //创建一个消费者
        NettyClient consumer = new NettyClient();

        //创建一个代理对象
        HelloService service = (HelloService) consumer.getBean(HelloService.class, providerName);

        //通过代理对象调用服务提供者的方法（服务）
        String res = service.hello("你好，dubbo~");
        System.out.println("结果：" + res);

    }

}
