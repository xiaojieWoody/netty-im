package netty.im.server.handler.request;

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
import netty.im.util.ServerSessionUtil;

import java.util.ArrayList;
import java.util.Iterator;
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
            createGroupResponsePacket.setCreateUserId(ServerSessionUtil.getSession(ctx.channel()).getUserId());
            ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
            List<String> extra = new ArrayList<>();
            Iterator<String> iterator = members.iterator();
            while (iterator.hasNext()) {
                String member = iterator.next();
                Channel channel = ServerSessionUtil.getChannelByUserId(member);
                if (channel != null) {
                    channelGroup.add(channel);
                    memberNames.add(ServerSessionUtil.getSession(channel));
                } else {
                    extra.add(member);
                    iterator.remove();
                }
            }
            if(extra.size() > 0) {
                extra.add(0, "不存在用户：");
                createGroupResponsePacket.setExtra(extra);
            }
            createGroupResponsePacket.setMembersInfo(memberNames);
            ServerSessionUtil.bindGroupChannel(groupId, channelGroup);
            log.info("组内成员：{}", memberNames);
            for (String member : members) {
                ServerSessionUtil.getChannelByUserId(member).writeAndFlush(createGroupResponsePacket);
            }
            createGroupResponsePacket.setSuccess(true);
        } else {
            createGroupResponsePacket.setSuccess(false);
            createGroupResponsePacket.setReason("成员不能为空！");
            ctx.channel().writeAndFlush(createGroupResponsePacket);
        }
    }
}
