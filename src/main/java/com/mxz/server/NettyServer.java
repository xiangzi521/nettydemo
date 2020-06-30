package com.mxz.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**  主要逻辑
 * Created by Administrator on 2020/6/15.
 */

@Slf4j
@Component
public class NettyServer {

    public void start(InetSocketAddress address){

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
        bootstrap.group(boss,worker).channel(NioServerSocketChannel.class)
                .localAddress(address)
                .childHandler(new ServerChannelInitializer()) // 自定义初始化条件
                .option(ChannelOption.SO_BACKLOG,128)
//                .localAddress("localhost",7000)
                .childOption(ChannelOption.SO_KEEPALIVE,true); // 表示可以重复使用端口
        // 绑定端口，开始接受进来的连接
            ChannelFuture sync = bootstrap.bind(address).sync();
            log.info("server start listen at ====>> {}" ,address.getPort());
            sync.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
