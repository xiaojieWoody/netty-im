package netty.im.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.Packet;
import netty.im.server.handler.request.*;
import netty.im.server.handler.response.ServerPushAllResponseHandler;
import netty.im.server.handler.response.ServerPushGroupResponseHandler;
import netty.im.server.handler.response.ServerPushSingleResponseHandler;

import java.util.HashMap;
import java.util.Map;

import static netty.im.protocol.command.Command.*;

@Slf4j
@ChannelHandler.Sharable
public class IMServerHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMServerHandler INSTANCE = new IMServerHandler();
//    private final Map<Byte, Class<? extends Packet>> handlerMap = new HashMap<>();
    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;
    private IMServerHandler(){
        handlerMap = new HashMap<>();

//        handlerMap.put(REQUEST_MESSAGE, RequestMessageHandler.class);
        // 单发消息
        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        // 创建群组请求
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        // 加入群组
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        // 退出群组请求
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        // 获取组内成员
        handlerMap.put(LIST_GROUP_MEMBER_REQUEST, ListGroupMemberRequestHandler.INSTANCE);
        // 给群组发送消息
        handlerMap.put(SEDN_MESSAGE_TO_GROUP_REQUEST, SendMessageToGroupRequestHandler.INSTANCE);

        // 单发消息响应处理
        handlerMap.put(SINGLE_PUSH_RESPONSE, ServerPushSingleResponseHandler.INSTANCE);
        // 群发消息响应处理
        handlerMap.put(GROUP_PUSH_RESPONSE, ServerPushGroupResponseHandler.INSTANCE);
        // Push All消息响应处理
        handlerMap.put(ALL_PUSH_RESPONSE, ServerPushAllResponseHandler.INSTANCE);
        // 心跳处理
        handlerMap.put(HEARTBEAT_REQUEST, HeartBeatRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        log.info("IMServerHandler .........channelRead0：{}", JSON.toJSONString(msg));
        //
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
