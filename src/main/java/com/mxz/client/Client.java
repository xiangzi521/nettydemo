package com.mxz.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Created by Administrator on 2020/6/15.
 */
@Slf4j
public class Client {


    public static void main(String[] args) {

        sendMessage();
    }

    public static void sendMessage() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ClientChannelInitializer());// 表示可以重复使用端口



            ChannelFuture sync = b.connect("10.40.2.138", 7000).sync();

            Scanner scanner = new Scanner(System.in);
            while (true){
                String s = scanner.nextLine();
                if (s.equals("exit")){
                    break;
                }
                sync.channel().writeAndFlush(s);
                log.info("发送信息-》{}",s);
            }
            //sync.channel().writeAndFlush(content);
            sync.channel().closeFuture().sync();
            log.info("sync.channel().closeFuture().sync() 执行了。。。");
        } catch (InterruptedException e) {
            group.shutdownGracefully();
        }


    }

}
