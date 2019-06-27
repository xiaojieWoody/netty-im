package netty.im.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.Packet;
import netty.im.protocol.PacketCodec;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {}

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("PacketCodecHandler decode ........{}", JSON.toJSONString(in));
        out.add(PacketCodec.INSTANCE.decode(in));
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, List<Object> out) throws Exception {
//        PacketCodec.INSTANCE.encode(out, msg);
//        ByteBuf byteBuf = ByteBufAllocator.io

//        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        log.info("PacketCodecHandler encode..............{}",JSON.toJSONString(in));
        PacketCodec.INSTANCE.encode(byteBuf, in);
        log.info("PacketCodecHandler byteBuf.............{}",JSON.toJSONString(byteBuf));
        out.add(byteBuf);
    }
}
