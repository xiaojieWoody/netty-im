package netty.im.server.handler.request;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.SendMessageToGroupRequestPacket;
import netty.im.protocol.response.SendMessageToGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class SendMessageToGroupRequestHandler extends SimpleChannelInboundHandler<SendMessageToGroupRequestPacket> {

    public static final SendMessageToGroupRequestHandler INSTANCE = new SendMessageToGroupRequestHandler();
    private SendMessageToGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessageToGroupRequestPacket msg) throws Exception {
        SendMessageToGroupResponsePacket sendMessageToGroupResponsePacket = new SendMessageToGroupResponsePacket();
        String groupId = msg.getGroupId();
        String message = msg.getMessage();
        String userId = ServerSessionUtil.getSession(ctx.channel()).getUserId();
        sendMessageToGroupResponsePacket.setMessage(message);
        sendMessageToGroupResponsePacket.setGroupId(groupId);
        sendMessageToGroupResponsePacket.setSendUserId(userId);
        sendMessageToGroupResponsePacket.setSendUserName(ServerSessionUtil.getSession(ctx.channel()).getUserName());
        // channel掉线后，group中channel是否还在？
        ChannelGroup channelGroup = ServerSessionUtil.getChannelGroup(groupId);
        if (channelGroup != null) {
            sendMessageToGroupResponsePacket.setSuccess(true);
            List<Session> groupMemeber = new ArrayList<>();
            for (Channel channel : channelGroup) {
                if(!ServerSessionUtil.getSession(channel).getUserId().equals(userId)) {
                    groupMemeber.add(ServerSessionUtil.getSession(channel));
                }
                channel.writeAndFlush(sendMessageToGroupResponsePacket);
            }
            sendMessageToGroupResponsePacket.setGroupMember(groupMemeber);
        } else {
            sendMessageToGroupResponsePacket.setSuccess(false);
            sendMessageToGroupResponsePacket.setReason("群组["+groupId+"]不存在！");
        }
        ctx.channel().writeAndFlush(sendMessageToGroupResponsePacket);
    }
}
