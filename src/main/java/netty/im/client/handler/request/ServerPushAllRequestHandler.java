package netty.im.client.handler.request;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import netty.im.protocol.request.ServerPushAllMessageRequestPacket;
import netty.im.protocol.response.ServerPushAllMessageResponsePacket;
import netty.im.session.Session;
import netty.im.util.ClientSessionUtil;

@Data
@ChannelHandler.Sharable
public class ServerPushAllRequestHandler extends SimpleChannelInboundHandler<ServerPushAllMessageRequestPacket> {

    public static final ServerPushAllRequestHandler INSTANCE = new ServerPushAllRequestHandler();
    private ServerPushAllRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ServerPushAllMessageRequestPacket msg) throws Exception {
        System.out.println("收到Server Push All : ["+msg.getMessage()+"]" );
        ServerPushAllMessageResponsePacket responsePacket = new ServerPushAllMessageResponsePacket();
        Session clientSession = ClientSessionUtil.getClientSession(ctx.channel());
        responsePacket.setMessage("["+clientSession.getUserId() + ":" + clientSession.getUserName()+"]收到Server Push All : ["+msg.getMessage()+"]" );
        ctx.channel().writeAndFlush(responsePacket);
    }
}
