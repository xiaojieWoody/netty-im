package netty.im.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.PacketCodec;

import java.util.List;

@Slf4j
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("PacketDecoder ............. {}", JSON.toJSONString(in));
        out.add(PacketCodec.INSTANCE.decode(in));
    }
}
