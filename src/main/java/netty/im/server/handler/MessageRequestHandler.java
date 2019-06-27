package netty.im.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.MessageRequestPacket;
import netty.im.protocol.response.MessageResponsePacket;
import netty.im.session.Session;
import netty.im.util.SessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
    private MessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
//        if(SessionUtil.hasLogin(ctx.channel())) {
//            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//            messageResponsePacket.setToUserId(messageRequestPacket.getUserId());
//            messageResponsePacket.setMessage(messageRequestPacket.getMessage());
//        } else {
//            System.out.println("");
//        }

        long startTime = System.currentTimeMillis();
        // 发送方Session
        Session sendSession = SessionUtil.getSession(ctx.channel());
        // 要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(sendSession.getUserId());
        messageResponsePacket.setFromUserName(sendSession.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        log.info("MessageRequestHandler channelRead0 .......{}", JSON.toJSONString(messageResponsePacket));
        // 消息接收方Channel
        Channel toUserChannel = SessionUtil.getChannelByUserId(messageRequestPacket.getToUserId());
        if(toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket).addListener(future -> {
//                if(future.isSuccess()) {
                if(future.isDone()) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("["+sendSession.getUserId()+":"+ sendSession.getUserName()+ "]发送消息["+messageRequestPacket.getMessage()+"]给["+messageRequestPacket.getToUserId()+"]成功，耗时：" + (endTime - startTime) + "毫秒");
                }
//                else {
//                    System.err.println("消息发送失败！");
//                }
            });
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "]不在线，发送失败！");
        }
    }
}
