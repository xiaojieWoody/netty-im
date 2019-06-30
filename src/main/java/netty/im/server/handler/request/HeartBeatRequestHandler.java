package netty.im.server.handler.request;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.HeartBeatRequestPacket;
import netty.im.protocol.response.HeartBeatResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();
    private HeartBeatRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        Session session = ServerSessionUtil.getSession(ctx.channel());
        if(ServerSessionUtil.hasLogin(ctx.channel())) {
            log.info("接收到客户端["+session.getUserId() + ":" + session.getUserName()+"]的心跳！");
            log.info("发送心跳给客户端["+session.getUserId() + ":" + session.getUserName()+"");
            ctx.writeAndFlush(new HeartBeatResponsePacket());
        }
    }
}
