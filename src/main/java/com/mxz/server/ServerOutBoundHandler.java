package com.mxz.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/6/15.
 */
@Slf4j
public class ServerOutBoundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info(" ServerOutBoundHandler write :[{}] ",msg.toString());
        ctx.write(msg,promise);
    }
}
