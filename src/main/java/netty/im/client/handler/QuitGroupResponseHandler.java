package netty.im.client.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.QuitGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.SessionUtil;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();
    private QuitGroupResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        String groupId = msg.getGroupId();
        List<Session> membersInfo = msg.getMembersInfo();
        String userId = msg.getUserId();
        String userName = msg.getUserName();
        log.info("............." + JSON.toJSONString(msg));
        if(userId.equals(SessionUtil.getSession(ctx.channel()).getUserId())) {
            System.out.println("您已退出["+groupId+"]群组!");
        } else {
            System.out.println("["+userId + ":" +userName+"]已经退出群组["+groupId+"]，组内剩下成员为["+JSON.toJSONString(membersInfo)+"]");
        }
    }
}
