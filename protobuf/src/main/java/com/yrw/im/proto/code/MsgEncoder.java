package com.yrw.im.proto.code;

import com.google.protobuf.Message;
import com.yrw.im.proto.constant.MsgTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 2019-04-14
 * Time: 16:35
 *
 * @author yrw
 */
public class MsgEncoder extends MessageToByteEncoder<Message> {
    private static final Logger logger = LoggerFactory.getLogger(MsgEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        try {
            byte[] bytes = msg.toByteArray();
            int code = MsgTypeEnum.getByClass(msg.getClass()).getCode();
            int length = bytes.length;

            ByteBuf buf = Unpooled.buffer(8 + length);
            buf.writeInt(length);
            buf.writeInt(code);
            buf.writeBytes(bytes);
            out.writeBytes(buf);

            logger.info("send message, remoteAddress: {}, content length {}, msgTypeCode: {}", ctx.channel().remoteAddress(), length, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[msg encoder], has error", cause);
    }
}
