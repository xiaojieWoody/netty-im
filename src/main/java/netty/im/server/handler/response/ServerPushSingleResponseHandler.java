package netty.im.server.handler.response;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.ServerPushSingleMessageResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class ServerPushSingleResponseHandler extends SimpleChannelInboundHandler<ServerPushSingleMessageResponsePacket> {

    public static final ServerPushSingleResponseHandler INSTANCE = new ServerPushSingleResponseHandler();
    private ServerPushSingleResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ServerPushSingleMessageResponsePacket msg) throws Exception {
        Session session = ServerSessionUtil.getSession(ctx.channel());
        System.out.println("单发消息给["+session.getUserId() +":"+session.getUserName() +"]成功，收到返回消息["+msg.getMessage()+"]");
    }
}
