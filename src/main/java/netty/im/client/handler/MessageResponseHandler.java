package netty.im.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.im.protocol.response.MessageResponsePacket;

//@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();
    private MessageResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println("收到[" + fromUserId + ":" + fromUserName + "]发送的消息：" + messageResponsePacket.getMessage());
    }
}
