package netty.im.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.CreateGroupRequestPacket;
import netty.im.protocol.response.CreateGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.IDUtil;
import netty.im.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
    private CreateGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> members = msg.getMembers();
        log.info("CreateGroupRequestHandler channelRead0 .....{}", JSON.toJSONString(members));
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        if(members != null && members.size() > 0) {
            String groupId = IDUtil.randomId();
            List<Session> memberNames = new ArrayList<>();
            createGroupResponsePacket.setGroupId(groupId);
            ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
            for (String member : members) {
                Channel channel = SessionUtil.getChannelByUserId(member);
                channelGroup.add(channel);
                memberNames.add(SessionUtil.getSession(channel));
            }
            createGroupResponsePacket.setMembersInfo(memberNames);
            SessionUtil.bindGroupChannel(groupId, channelGroup);
            log.info("组内成员：{}", memberNames);
            for (String member : members) {
                SessionUtil.getChannelByUserId(member).writeAndFlush(createGroupResponsePacket);
            }
        }
    }
}
