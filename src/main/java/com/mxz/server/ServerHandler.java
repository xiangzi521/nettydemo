package com.mxz.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Created by Administrator on 2020/6/15.
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
  /*  @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ServerHandler channelActive....");
    }

    *//**
     * 接受客户端传递过来的信息
     * @param ctx
     * @param msg
     * @throws Exception
     *//*
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ServerHandler channelRead....");
        log.info("[{}], server :  [{}]",ctx.channel().remoteAddress(),msg.toString());
        String s = msg.toString() + "  客户端发送信息";
        *//*ctx.write("server  callback to client -> " + s);
        ctx.flush();*//*
        ctx.writeAndFlush("server  callback to client -> " + s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }*/

    // ==============上面是做为一个服务器的使用====下面是类似聊天室的======================


    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel()); // 加入
        log.info( "[{}] | [{}] come into the chattingroom, online [{}]",ctx.name(),ctx.channel().id(),channels.size());
        log.info(" channels.size() ==> [{}] ",channels.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info( "[{}]left the chattingroom, online [{}]",ctx.channel().id(),channels.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ServerHandler  channelActive ...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("{} server 收到信息 {}",ctx.name().toString(),msg.toString());
        for (Channel channel : channels) {
            channel.writeAndFlush(channel.id()+"  服务端返回的信息 ==>> "+msg.toString());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.channel().id() + " concurrent into error, online"+channels.size() );
        log.info("[{}] concurrent into error, online [{}]",ctx.channel().id(),channels.size());
        ctx.close();
    }
}
