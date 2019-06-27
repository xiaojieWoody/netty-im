package netty.im.client.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.Packet;

import java.util.HashMap;
import java.util.Map;

import static netty.im.protocol.command.Command.MESSAGE_RESPONSE;
import static netty.im.protocol.command.Command.SINGLE_PUSH;

@Slf4j
@ChannelHandler.Sharable
public class IMClientHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMClientHandler INSTANCE = new IMClientHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> clientHandlerMap;

    private IMClientHandler() {
        clientHandlerMap = new HashMap<>();

        clientHandlerMap.put(Byte.valueOf(SINGLE_PUSH), ServerPushSingleHandler.INSTANCE);
        clientHandlerMap.put(MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        log.info("IMClientHandler {}",JSON.toJSONString(msg));
        clientHandlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
