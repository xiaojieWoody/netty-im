package netty.im.client.handler.response;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.NettyClient;
import netty.im.client.handler.HeartBeatTimerHandler;
import netty.im.protocol.response.LoginResponsePacket;
import netty.im.session.Session;
import netty.im.util.ClientSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();
    private LoginResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();
        if(loginResponsePacket.isSuccess()) {
            System.out.println("Hi, 登录成功！["+userId + ":" + userName+"]");
            log.info("LoginResponseHandler {} : {} 登录成功", userId, userName);
            // 这里没绑定会导致连接断开，重复登录
//            ServerSessionUtil.bindSession(new Session(userId, userName), ctx.channel());
            ClientSessionUtil.bindClientSession(new Session(userId, userName), ctx.channel());
        } else {
            log.error("LoginResponseHandler {}：{} 登录失败，原因：{}", userId, userName, loginResponsePacket.getReason());
            System.out.println("登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    // 服务端掉线后触发该事件，客户端连接被关闭
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
//        // 重新连接  有问题
//        NettyClient.connectServer();
    }
}
