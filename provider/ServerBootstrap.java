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
