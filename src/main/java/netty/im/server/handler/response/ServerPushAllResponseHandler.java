package netty.im.server.handler.response;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import netty.im.protocol.response.ServerPushAllMessageResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

import java.util.List;

@Data
@ChannelHandler.Sharable
public class ServerPushAllResponseHandler extends SimpleChannelInboundHandler<ServerPushAllMessageResponsePacket> {

    public static final ServerPushAllResponseHandler INSTANCE = new ServerPushAllResponseHandler();
    private ServerPushAllResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ServerPushAllMessageResponsePacket msg) throws Exception {
        Session session = ServerSessionUtil.getSession(ctx.channel());
        List<String> strings = ServerSessionUtil.listGroupIdByChannel(ctx.channel());
        if(strings.size() > 0) {
            System.out.println("Push All : 收到群组["+strings+"]的["+session.getUserId() + ":" + session.getUserName()+"]的回复消息["+msg.getMessage()+"]");
        } else {
            System.out.println("Push All : 收到["+session.getUserId() + ":" + session.getUserName()+"]的回复消息["+msg.getMessage()+"]");
        }

    }
}
