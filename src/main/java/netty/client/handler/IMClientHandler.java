package netty.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.common.protocol.Packet;

import java.util.HashMap;
import java.util.Map;

import static netty.common.protocol.command.Command.MESSAGE_RESPONSE;

@ChannelHandler.Sharable
public class IMClientHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMClientHandler INSTANCE = new IMClientHandler();
    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> clientHandlerMap;
    private IMClientHandler() {
        clientHandlerMap = new HashMap<>();
        // 消息响应处理
        clientHandlerMap.put(MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        clientHandlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
