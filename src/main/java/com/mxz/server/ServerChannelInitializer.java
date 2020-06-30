package com.mxz.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


/**
 * Created by Administrator on 2020/6/15.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(io.netty.channel.socket.SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast(new ServerHandler());
//        socketChannel.pipeline().addLast(new ServerOutBoundHandler());
    }
}
