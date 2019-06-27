package netty.im.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.MessageResponsePacket;

@Slf4j
//@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();
    private MessageResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        log.info("MessageResponseHandler fromUserId:{}, fromUserName:{}, message:{}" ,fromUserId, fromUserName,messageResponsePacket.getMessage());
        System.out.println("收到[" + fromUserId + ":" + fromUserName + "]发送的消息：" + messageResponsePacket.getMessage());
    }
}
