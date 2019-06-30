package netty.im.client.handler.response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.MessageResponsePacket;
import netty.im.util.ClientSessionUtil;

@Slf4j
//@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();
    private MessageResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {

        if(messageResponsePacket.isSuccess()){

            String fromUserId = messageResponsePacket.getFromUserId();
            String fromUserName = messageResponsePacket.getFromUserName();
            String toUserId = messageResponsePacket.getToUserId();
            String userId = ClientSessionUtil.getClientSession(ctx.channel()).getUserId();

            log.info("MessageResponseHandler fromUserId:{}, fromUserName:{}, message:{}" ,fromUserId, fromUserName,messageResponsePacket.getMessage());
            if(userId.equals(fromUserId)) {
                System.out.println("消息["+messageResponsePacket.getMessage()+"]已发送给["+messageResponsePacket.getToUserId()+"]");
            }
            if(userId.equals(toUserId)) {
                System.out.println("收到[" + fromUserId + ":" + fromUserName + "]发送的消息：" + messageResponsePacket.getMessage());
            }
        } else {
            System.out.println("消息发送失败，原因：" + messageResponsePacket.getReason());
        }
    }
}
