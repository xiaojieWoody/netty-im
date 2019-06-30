package netty.im.client.handler.response;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.ListGroupMemberResponsePacket;

@Slf4j
@ChannelHandler.Sharable
public class ListGroupMemeberResponseHandler extends SimpleChannelInboundHandler<ListGroupMemberResponsePacket> {

    public static final ListGroupMemeberResponseHandler INSTANCE = new ListGroupMemeberResponseHandler();

    private ListGroupMemeberResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMemberResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("群组[" + msg.getGroupId() + "]中的成员有：" + JSON.toJSONString(msg.getGroupMembers()));
        } else {
            System.out.println("查看群组[" + msg.getGroupId() + "]成员失败，原因：" + msg.getReason());
        }
    }
}
