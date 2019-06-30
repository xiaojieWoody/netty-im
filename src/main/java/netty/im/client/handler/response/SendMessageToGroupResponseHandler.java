package netty.im.client.handler.response;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.response.SendMessageToGroupResponsePacket;
import netty.im.util.ClientSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class SendMessageToGroupResponseHandler extends SimpleChannelInboundHandler<SendMessageToGroupResponsePacket> {

    public static final SendMessageToGroupResponseHandler INSTANCE = new SendMessageToGroupResponseHandler();
    private SendMessageToGroupResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessageToGroupResponsePacket msg) throws Exception {
        String sendUserId = msg.getSendUserId();
        String userId = ClientSessionUtil.getClientSession(ctx.channel()).getUserId();
        if(msg.isSuccess()) {
            if(!sendUserId.equals(userId)) {
                System.out.println("收到["+msg.getGroupId()+"]群中["+msg.getSendUserId() + ":" + msg.getSendUserName()+"]群发的消息["+msg.getMessage()+"]");
            } else {
                System.out.println("群发消息["+msg.getMessage()+"]成功！,收到消息的用户有：" + JSON.toJSONString(msg.getGroupMember()));
            }
        } else {
            if(userId.equals(sendUserId)) {
                System.out.println("消息群发失败，原因：" + msg.getReason());
            }
        }
    }
}
