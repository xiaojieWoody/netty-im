package netty.im.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.im.protocol.Packet;
import netty.im.protocol.PacketCodec;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) throws Exception {
        PacketCodec.INSTANCE.encode(out, in);
    }
}
