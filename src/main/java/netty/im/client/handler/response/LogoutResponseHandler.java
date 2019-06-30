package netty.im.client.handler.response;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.LogoutResponsePacket;
import netty.im.util.ClientSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    public static final LogoutResponseHandler INSTANCE = new LogoutResponseHandler();
    private LogoutResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        if(msg.isSuccess()) {
            ClientSessionUtil.unBindClientSession(ctx.channel());
            System.err.println("退出登录成功！");
        } else {
            System.err.println("退出登录失败！");
        }
    }
}
