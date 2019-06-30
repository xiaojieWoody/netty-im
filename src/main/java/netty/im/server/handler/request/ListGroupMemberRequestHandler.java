package netty.im.server.handler.request;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.ListGroupMemberRequestPacket;
import netty.im.protocol.response.ListGroupMemberResponsePacket;
import netty.im.session.Session;
import netty.im.util.ServerSessionUtil;

import java.util.List;

@ChannelHandler.Sharable
@Slf4j
public class ListGroupMemberRequestHandler extends SimpleChannelInboundHandler<ListGroupMemberRequestPacket> {

    public static final ListGroupMemberRequestHandler INSTANCE = new ListGroupMemberRequestHandler();
    private ListGroupMemberRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMemberRequestPacket msg) throws Exception {
        ListGroupMemberResponsePacket listGroupMemberResponsePacket = new ListGroupMemberResponsePacket();
        String groupId = msg.getGroupId();
        List<Session> groupMember = ServerSessionUtil.getGroupMember(groupId);
        listGroupMemberResponsePacket.setGroupId(groupId);
        if (groupMember.size() != 0) {
            listGroupMemberResponsePacket.setSuccess(true);
            listGroupMemberResponsePacket.setGroupId(groupId);
            listGroupMemberResponsePacket.setGroupMembers(groupMember);
        } else {
            listGroupMemberResponsePacket.setSuccess(false);
            listGroupMemberResponsePacket.setReason("群组["+groupId+"]不存在！");
        }
        ctx.channel().writeAndFlush(listGroupMemberResponsePacket);
    }
}
