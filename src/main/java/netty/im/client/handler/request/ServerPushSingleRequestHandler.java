package netty.im.client.handler.request;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.ServerPushSingleMessageRequestPacket;
import netty.im.protocol.response.ServerPushSingleMessageResponsePacket;

@Slf4j
public class ServerPushSingleRequestHandler extends SimpleChannelInboundHandler<ServerPushSingleMessageRequestPacket> {

    public static final ServerPushSingleRequestHandler INSTANCE = new ServerPushSingleRequestHandler();
    private ServerPushSingleRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ServerPushSingleMessageRequestPacket msg) throws Exception {
        System.out.println("收到服务端推送消息：" + msg.getMessage());
        ServerPushSingleMessageResponsePacket responsePacket = new ServerPushSingleMessageResponsePacket();
        responsePacket.setMessage("["+msg.getUserId()+"]已收到服务端推送的单条信息["+msg.getMessage()+"]");
        ctx.channel().writeAndFlush(responsePacket);
    }
}
