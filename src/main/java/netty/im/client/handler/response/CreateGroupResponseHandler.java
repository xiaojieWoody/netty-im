package netty.im.client.handler.response;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.CreateGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.ClientSessionUtil;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();
    private CreateGroupResponseHandler(){};

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        List<Session> membersInfo = msg.getMembersInfo();
        String groupId = msg.getGroupId();
        ClientSessionUtil.bindClientGroup(ctx.channel(), groupId, membersInfo);
        if(ClientSessionUtil.getClientSession(ctx.channel()).getUserId().equals(msg.getCreateUserId())) {
            System.out.println("您已创建GroupID为:["+groupId+"]的群组,成员有："+JSON.toJSONString(membersInfo));
        } else {
            System.out.println("您已加入GroupID为:["+groupId+"]的群组,成员有："+JSON.toJSONString(membersInfo));
        }

    }
}
