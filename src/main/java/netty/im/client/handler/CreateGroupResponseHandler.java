package netty.im.client.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.CreateGroupResponsePacket;
import netty.im.session.Session;

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
        System.out.println("创建Group成功，GroupID为:["+groupId+"],成员有："+JSON.toJSONString(membersInfo));
    }
}
