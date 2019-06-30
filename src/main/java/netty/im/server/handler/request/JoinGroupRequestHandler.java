package netty.im.server.handler.request;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.JoinGroupRequestPacket;
import netty.im.protocol.response.JoinGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();
    private JoinGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = ServerSessionUtil.getChannelGroup(groupId);
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        if (channelGroup != null) {
            List<Session> groupMemebers = new ArrayList<>();
            channelGroup.add(ctx.channel());
            joinGroupResponsePacket.setGroupId(groupId);
            Session session = ServerSessionUtil.getSession(ctx.channel());
            joinGroupResponsePacket.setUserId(session.getUserId());
            joinGroupResponsePacket.setUserName(session.getUserName());
            joinGroupResponsePacket.setSuccess(true);
            for (Channel channel : channelGroup) {
                groupMemebers.add(ServerSessionUtil.getSession(channel));
            }
            for (Channel channel : channelGroup) {
                channel.writeAndFlush(joinGroupResponsePacket);
            }
        } else {
            joinGroupResponsePacket.setSuccess(false);
            joinGroupResponsePacket.setReason("群组["+groupId+"]不存在！");
            ctx.channel().writeAndFlush(joinGroupResponsePacket);
        }

    }
}
