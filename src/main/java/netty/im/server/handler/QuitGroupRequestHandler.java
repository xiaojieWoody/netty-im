package netty.im.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.QuitGroupRequestPacket;
import netty.im.protocol.response.QuitGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();
    private QuitGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup != null) {
            Session session = SessionUtil.getSession(ctx.channel());
            QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
            quitGroupResponsePacket.setUserId(session.getUserId());
            quitGroupResponsePacket.setUserName(session.getUserName());
            quitGroupResponsePacket.setGroupId(groupId);
            List<Session> memebersInfo = new ArrayList<>();
            channelGroup.remove(ctx.channel());

            for (Channel channel : channelGroup) {
                memebersInfo.add(SessionUtil.getSession(channel));
            }
            quitGroupResponsePacket.setMembersInfo(memebersInfo);
            for (Channel channel : channelGroup) {
                // 发给组内user
                channel.writeAndFlush(quitGroupResponsePacket);
            }
            //发给退组user
            ctx.channel().writeAndFlush(quitGroupResponsePacket);
        }
    }
}
