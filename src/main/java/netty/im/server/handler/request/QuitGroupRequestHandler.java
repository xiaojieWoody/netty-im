package netty.im.server.handler.request;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.QuitGroupRequestPacket;
import netty.im.protocol.response.QuitGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();
    private QuitGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = ServerSessionUtil.getChannelGroup(groupId);
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        if (channelGroup != null) {
            Session session = ServerSessionUtil.getSession(ctx.channel());
            quitGroupResponsePacket.setUserId(session.getUserId());
            quitGroupResponsePacket.setUserName(session.getUserName());
            quitGroupResponsePacket.setGroupId(groupId);
            quitGroupResponsePacket.setSuccess(true);
//            List<Session> memebersInfo = new ArrayList<>();
            channelGroup.remove(ctx.channel());

//            for (Channel channel : channelGroup) {
//                memebersInfo.add(ServerSessionUtil.getSession(channel));
//            }
//            quitGroupResponsePacket.setMembersInfo(memebersInfo);
            for (Channel channel : channelGroup) {
                // 发给组内user
                channel.writeAndFlush(quitGroupResponsePacket);
            }
            if(channelGroup.size() == 0) {
                ServerSessionUtil.removeGroup(groupId);
            }
        } else {
            quitGroupResponsePacket.setSuccess(false);
            quitGroupResponsePacket.setReason("群组不存在！");
        }

        //发给退组user
        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
