package netty.im.client.handler.response;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.HeartBeatResponsePacket;

@Slf4j
@ChannelHandler.Sharable
public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {

    public static final HeartBeatResponseHandler INSTANCE = new HeartBeatResponseHandler();
    private HeartBeatResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket msg) throws Exception {
        log.info("收到服务端心跳返回！");
    }
}
