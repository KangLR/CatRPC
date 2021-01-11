# CatRPC

一个基于Netty的远程过程调用Demo

## 接口

1. HelloService

```java
package com.kang.io.netty.dubborpc.api;

public interface HelloService {
    String hello(String mes);
}
```

## 服务提供者

1. 实现了HelloService接口的服务类

   ```java
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
   ```

2. 服务启动（调用Netty服务端）

   ```java
   package com.kang.io.netty.dubborpc.provider;
   
   import com.kang.io.netty.dubborpc.netty.NettyServer;
   
   /**
    * @author klr
    * @create 2021-01-11-18:49
    */
   //启动一个服务提供者，就是NettyServer
   public class ServerBootstrap {
       public static void main(String[] args) {
           NettyServer.startServer("127.0.0.1", 9001);
       }
   }
   ```

## 服务消费者

1. 客户端启动（调用Netty客户端）

   ```java
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
   ```

   

