package netty.im.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.im.protocol.response.LoginResponsePacket;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {

        if(loginResponsePacket.isSuccess()) {
            System.out.println("Hi, 登录成功！");
        } else {
            System.out.println("登录失败，原因：" + loginResponsePacket.getReason());
        }
    }
}
