package netty.im.server.handler.response;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.ServerPushGroupMessageResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class ServerPushGroupResponseHandler extends SimpleChannelInboundHandler<ServerPushGroupMessageResponsePacket> {

    public static final ServerPushGroupResponseHandler INSTANCE = new ServerPushGroupResponseHandler();
    private ServerPushGroupResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ServerPushGroupMessageResponsePacket msg) throws Exception {
        Session session = ServerSessionUtil.getSession(ctx.channel());
        System.out.println("群发消息：已收到["+session.getUserId() + ":" + session.getUserName()+"]的回复消息：["+msg.getMessage()+"]");
    }
}
