package netty.im.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.im.protocol.Packet;

import java.util.HashMap;
import java.util.Map;

import static netty.im.protocol.command.Command.MESSAGE_REQUEST;

@ChannelHandler.Sharable
public class IMServerHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMServerHandler INSTANCE = new IMServerHandler();
//    private final Map<Byte, Class<? extends Packet>> handlerMap = new HashMap<>();
    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;
    private IMServerHandler(){
        handlerMap = new HashMap<>();

//        handlerMap.put(REQUEST_MESSAGE, RequestMessageHandler.class);
        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        //
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
