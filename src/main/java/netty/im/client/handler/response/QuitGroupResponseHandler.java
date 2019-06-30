package netty.im.client.handler.response;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.QuitGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.ClientSessionUtil;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();
    private QuitGroupResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        String groupId = msg.getGroupId();
        String userId = msg.getUserId();
        String userName = msg.getUserName();
        ClientSessionUtil.quitClientGroup(ctx.channel(), userId, groupId);
        List<Session> membersInfo = ClientSessionUtil.listGroupUsers(ctx.channel(), groupId);
        log.info("............." + JSON.toJSONString(msg));
        if(userId.equals(ClientSessionUtil.getClientSession(ctx.channel()).getUserId())) {
            System.out.println("您已退出["+groupId+"]群组!");
        } else {
            System.out.println("["+userId + ":" +userName+"]已经退出群组["+groupId+"]，组内剩下成员为["+JSON.toJSONString(membersInfo)+"]");
        }
    }
}
