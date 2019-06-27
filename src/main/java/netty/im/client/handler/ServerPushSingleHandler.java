package netty.im.client.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.ServerPushSingleMessagePacket;

@Slf4j
public class ServerPushSingleHandler extends SimpleChannelInboundHandler<ServerPushSingleMessagePacket> {

    public static final ServerPushSingleHandler INSTANCE = new ServerPushSingleHandler();
    private ServerPushSingleHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ServerPushSingleMessagePacket msg) throws Exception {
        log.info(".....收到服务端推送消息...{}" ,JSON.toJSONString(msg));
        System.out.println("收到服务端推送消息：" + msg.getMessage());
    }
}
