package netty.im.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.JoinGroupRequestPacket;
import netty.im.protocol.response.JoinGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.SessionUtil;

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
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        if (channelGroup != null) {
            List<Session> groupMemebers = new ArrayList<>();
            channelGroup.add(ctx.channel());
            joinGroupResponsePacket.setGroupId(groupId);
            Session session = SessionUtil.getSession(ctx.channel());
            joinGroupResponsePacket.setUserId(session.getUserId());
            joinGroupResponsePacket.setUserName(session.getUserName());
            for (Channel channel : channelGroup) {
                groupMemebers.add(SessionUtil.getSession(channel));
            }
            joinGroupResponsePacket.setGroupMemebers(groupMemebers);
            for (Channel channel : channelGroup) {
                channel.writeAndFlush(joinGroupResponsePacket);
            }
        }

    }
}
