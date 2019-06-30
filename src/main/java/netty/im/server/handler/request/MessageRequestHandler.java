package netty.im.server.handler.request;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.MessageRequestPacket;
import netty.im.protocol.response.MessageResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
    private MessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
//        if(ServerSessionUtil.hasLogin(ctx.channel())) {
//            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//            messageResponsePacket.setToUserId(messageRequestPacket.getUserId());
//            messageResponsePacket.setMessage(messageRequestPacket.getMessage());
//        } else {
//            System.out.println("");
//        }

        long startTime = System.currentTimeMillis();
        // 发送方Session
        Session sendSession = ServerSessionUtil.getSession(ctx.channel());
        // 要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(sendSession.getUserId());
        messageResponsePacket.setFromUserName(sendSession.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        log.info("MessageRequestHandler channelRead0 .......{}", JSON.toJSONString(messageResponsePacket));
        // 消息接收方Channel
        Channel toUserChannel = ServerSessionUtil.getChannelByUserId(messageRequestPacket.getToUserId());
        if(toUserChannel != null && ServerSessionUtil.hasLogin(toUserChannel)) {
            messageResponsePacket.setSuccess(true);
            messageResponsePacket.setToUserId(messageRequestPacket.getToUserId());
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            messageResponsePacket.setSuccess(false);
            messageResponsePacket.setReason("[" + messageRequestPacket.getToUserId() + "]不在线，发送失败！");
            System.err.println("[" + messageRequestPacket.getToUserId() + "]不在线，发送失败！");
        }
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
