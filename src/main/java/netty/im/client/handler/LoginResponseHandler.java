package netty.im.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.im.protocol.response.LoginResponsePacket;
import netty.im.session.Session;
import netty.im.util.SessionUtil;

@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();
    private LoginResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();
        if(loginResponsePacket.isSuccess()) {
            System.out.println("Hi, 登录成功！");
            // 这里没绑定会导致连接断开，重复登录
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println("登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
