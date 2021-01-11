package com.kang.io.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author klr
 * @create 2021-01-11-19:40
 */
public class NettyClient {

    //创建线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(8);

    private static NettyClientHandler client;

    //使用代理模式，获取代理对象
    //其实就是自动封装了个协议头
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, (proxy, method, args) -> {
            if (client == null) {
                initClient();
            }
            //设置发送给服务器的信息
            //providerName 协议头
            //args[0] 第一个参数
            client.setPara(providerName + args[0]);//hello方法的参数
            //执行的不是代理对象的方法，而是远程调用的方法
            System.out.println("-------");
            String result = (String) executorService.submit(client).get();
            return result;
        });
    }


    //初始化客户端
    private static void initClient() {
        client = new NettyClientHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    .group(group)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(client);
                        }
                    });
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 9001).sync();
//            cf.channel().closeFuture().sync();//加了这行就无效了
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
