package com.imdemo.codec;

import com.imdemo.codec.interfaces.Encoder;
import com.imdemo.protocol.ProtocolBase;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class ProtocolEncoder extends MessageToByteEncoder<ProtocolBase> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolBase msg, ByteBuf out) throws Exception {
//        // 记录初始写入位置
//        int startIndex = out.writerIndex();
//
//        // 占位长度字段
//        out.writeLong(0); // 假设8字节长度字段
//
//        // 写入消息元数据
//        out.writeByte(msg.getMethod());
//        out.writeLong(msg.getSequenceId());

        // 获取并调用合适的编码器
        Encoder<? extends ProtocolBase> encoder = CodecManager.getEncoder(msg.getMethod());
        if (encoder != null) {
            encodeMessage(encoder, out, msg);
            System.out.println("[encodeMessage] done");
//            System.out.println("[ProtocolEncoder] to execute ctx.writeAndFlush(out);");
//            ctx.writeAndFlush(out);
//            System.out.println("[ProtocolEncoder] flushed response " + msg.getMethod());
        } else {
            System.err.println("No encoder found for message type: " + msg.getMethod());
        }

//        // 计算并写入实际的消息长度
//        int endIndex = out.writerIndex();
//        int messageLength = endIndex - startIndex; // 总长度计算
//        out.setLong(startIndex, messageLength); // 更新占位符为实际长度
    }

    @SuppressWarnings("unchecked")
    private <T extends ProtocolBase> void encodeMessage(Encoder<T> encoder, ByteBuf out, ProtocolBase msg) throws Exception {
        encoder.encode(out, (T) msg);
    }
}
