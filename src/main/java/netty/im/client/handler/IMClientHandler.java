package netty.im.client.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.handler.request.ServerPushAllRequestHandler;
import netty.im.client.handler.request.ServerPushGroupRequestHandler;
import netty.im.client.handler.request.ServerPushSingleRequestHandler;
import netty.im.client.handler.response.*;
import netty.im.protocol.Packet;

import java.util.HashMap;
import java.util.Map;

import static netty.im.protocol.command.Command.*;

@Slf4j
@ChannelHandler.Sharable
public class IMClientHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMClientHandler INSTANCE = new IMClientHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> clientHandlerMap;

    private IMClientHandler() {
        clientHandlerMap = new HashMap<>();

        clientHandlerMap.put(Byte.valueOf(SINGLE_PUSH_REQUEST), ServerPushSingleRequestHandler.INSTANCE);
        clientHandlerMap.put(MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
        clientHandlerMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponseHandler.INSTANCE);
        clientHandlerMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponseHandler.INSTANCE);
        clientHandlerMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponseHandler.INSTANCE);
        clientHandlerMap.put(LIST_GROUP_MEMBER_RESPONSE, ListGroupMemeberResponseHandler.INSTANCE);
        clientHandlerMap.put(SEND_MESSAGE_TO_GROUP_RESPONSE, SendMessageToGroupResponseHandler.INSTANCE);

        clientHandlerMap.put(SINGLE_PUSH_REQUEST, ServerPushSingleRequestHandler.INSTANCE);
        clientHandlerMap.put(GROUP_PUSH_REQUEST, ServerPushGroupRequestHandler.INSTANCE);
        clientHandlerMap.put(ALL_PUSH_REQUEST, ServerPushAllRequestHandler.INSTANCE);

        clientHandlerMap.put(HEARTBEAT_RESPONSE, HeartBeatResponseHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        log.info("IMClientHandler {}",JSON.toJSONString(msg));
        clientHandlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
