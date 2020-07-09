package com.mxz.client;

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

import java.util.Scanner;

/**456
 * Created by Administrator on 2020/6/15.
 */
public class Client1 {


    public static void main(String[] args) {
        sendMessage();
    }

    public static void sendMessage() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast("decoder",new StringDecoder());
                        p.addLast("encoder",new StringEncoder());
                        p.addLast(new ClientHandler());
                    }
                });

            ChannelFuture sync = b.connect("10.40.2.138", 7000).sync();
            Scanner scanner = new Scanner(System.in);
            while (true){
                String s = scanner.nextLine();
                if (s.equals("exit")){
                    break;
                }
                sync.channel().writeAndFlush(s);
            }
//            sync.channel().writeAndFlush(content);
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }


    }

}
