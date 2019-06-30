package netty.im.server.handler.request;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.LogoutRequestPacket;
import netty.im.protocol.response.LogoutResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();
    private LogoutRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        Session session = ServerSessionUtil.getSession(ctx.channel());
        System.err.println("["+session.getUserId() + ":" + session.getUserName()+"]退出登录！");
        ServerSessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket responsePacket = new LogoutResponsePacket();
        responsePacket.setSuccess(true);
        ctx.writeAndFlush(responsePacket);
    }
}
