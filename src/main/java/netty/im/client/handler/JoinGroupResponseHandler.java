package netty.im.client.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.JoinGroupResponsePacket;
import netty.im.session.Session;
import netty.im.util.SessionUtil;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();
    private JoinGroupResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {

        String groupId = msg.getGroupId();
        String userId = msg.getUserId();
        String userName = msg.getUserName();
        List<Session> groupMemebers = msg.getGroupMemebers();
//        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
//        if (channelGroup != null) {
//            List<Session> sessions = SessionUtil.allGroupUser(groupId);
//            for (Channel channel : channelGroup) {
//                if(SessionUtil.getSession(ctx.channel()).getUserId().equals(userId)) {
//                    System.out.println("您已经加入群组["+groupId+"]，组内成员有:" + JSON.toJSONString(sessions));
//                } else {
//                    System.out.println("["+userId + ":" +userName+"]已经加入群组["+groupId+"],组内成员有:" + JSON.toJSONString(sessions));
//                }
//            }
//        }

        System.out.println("["+userId + ":" +userName+"]已经加入群组["+groupId+"],组内成员有:" + JSON.toJSONString(groupMemebers));
    }
}
