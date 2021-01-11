package com.kang.io.netty.dubborpc.provider;

import com.kang.io.netty.dubborpc.api.HelloService;

/**
 * @author klr
 * @create 2021-01-11-18:18
 */

//服务提供者提供接口实现方法
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息=" + mes);
        if (mes != null) {
            return "你好客户端，我已经收到你的消息 [" + mes + "]";
        } else {
            return "你好客户端，我已经收到你的消息";
        }
    }
}
