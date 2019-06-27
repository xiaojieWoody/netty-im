package netty.im.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.Packet;
import netty.im.protocol.PacketCodec;

@Slf4j
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) throws Exception {
        log.info("PacketEncoder ........... {}", JSON.toJSONString(in));
        PacketCodec.INSTANCE.encode(out, in);
    }
}
