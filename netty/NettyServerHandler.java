package com.kang.io.netty.dubborpc.netty;

import com.kang.io.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author klr
 * @create 2021-01-11-19:01
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg= " + msg);

        //客户端在调用服务器API时，我们需要定义一个协议
        //比如每次发消息时以某个字符串开头 “HelloService#hello#"
        if (msg.toString().startsWith("HelloService#hello#")) {
            //这样定义不好，怕传过来的有#
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
