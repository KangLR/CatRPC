package com.kang.io.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author klr
 * @create 2021-01-11-19:23
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;//channel上下文
    private String result;//远程调用返回的结果
    private String para;//客户端调用方法时，传入的参数


    void setPara(String para) {
        this.para = para;
    }

    //异步任务回调
    //背代理对象调用，发送数据给服务器,wait->等待被唤醒
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        wait();
        System.out.println("返回");
        return result;
    }

    //与服务器连接创建后被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;//其他方法会使用到ctx
    }

    //收到服务器的数据后
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();//得到结果后唤醒call方法
        System.out.println("唤醒");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
