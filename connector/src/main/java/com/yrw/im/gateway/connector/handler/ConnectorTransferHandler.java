package com.yrw.im.gateway.connector.handler;

import com.yrw.im.proto.generate.Internal;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将消息发送给transfer
 * Date: 2019-02-12
 * Time: 12:17
 *
 * @author yrw
 */
public class ConnectorTransferHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private Logger logger = LoggerFactory.getLogger(ConnectorTransferHandler.class);

    private static ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[ConnectorTransfer] connect success");
        ConnectorTransferHandler.ctx = ctx;

        Internal.InternalMsg greet = Internal.InternalMsg.newBuilder()
            .setVersion(1)
            .setMsgType(Internal.InternalMsg.InternalMsgType.GREET)
            .setFrom(Internal.InternalMsg.Module.CONNECTOR)
            .setDest(Internal.InternalMsg.Module.TRANSFER)
            .setCreateTime(System.currentTimeMillis())
            .setMsgBody(ctx.channel().id().asLongText())
            .build();

        ctx.writeAndFlush(greet);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    }

    public static ChannelHandlerContext getCtx() {
        return ctx;
    }
}
