package netty.im.client.handler.request;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.ServerPushGroupMessageRequestPacket;
import netty.im.protocol.response.ServerPushGroupMessageResponsePacket;
import netty.im.session.Session;
import netty.im.util.ClientSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class ServerPushGroupRequestHandler extends SimpleChannelInboundHandler<ServerPushGroupMessageRequestPacket> {

    public static final ServerPushGroupRequestHandler INSTANCE = new ServerPushGroupRequestHandler();
    private ServerPushGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ServerPushGroupMessageRequestPacket msg) throws Exception {
        System.out.println("已收到服务端发送给群组["+msg.getGroupId()+"]的消息["+msg.getMessage()+"]");
        Session clientSession = ClientSessionUtil.getClientSession(ctx.channel());
        ServerPushGroupMessageResponsePacket serverPushGroupMessageResponsePacket = new ServerPushGroupMessageResponsePacket();
        serverPushGroupMessageResponsePacket.setMessage("["+clientSession.getUserId() + ":" + clientSession.getUserName()  +"]已收到服务端发送给群组["+msg.getGroupId()+"]的消息["+msg.getMessage()+"]");
        ctx.channel().writeAndFlush(serverPushGroupMessageResponsePacket);
    }
}
