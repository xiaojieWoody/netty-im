package netty.im.server.handler.request;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.LoginRequestPacket;
import netty.im.protocol.response.LoginResponsePacket;
import netty.im.session.Session;
import netty.im.util.IDUtil;
import netty.im.util.ServerSessionUtil;

@Slf4j
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    private LoginRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
//        String userId = IDUtil.randomId();
//        String userName = loginRequestPacket.getUserName();
//        Session session = new Session(userId, userName);
//        ServerSessionUtil.bindSession(session, ctx.channel());
//
//        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
//        if(loginRequestPacket.getPassword().equals("pwd")) {
//            loginResponsePacket.setSuccess(true);
//        } else {
//            loginResponsePacket.setSuccess(false);
//            loginResponsePacket.setReason("密码错误，登录失败！");
//        }
//        ctx.channel().writeAndFlush(loginResponsePacket);
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());
        log.info("LoginRequestHandler channelRead0...... {}", JSON.toJSONString(loginResponsePacket));
        if(valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IDUtil.randomId();
            String userName = loginRequestPacket.getUserName();
            loginResponsePacket.setUserId(userId);
            System.out.println("用户：["+ userId + ":" + userName + "]登录成功！");
            ServerSessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码错误！");
            System.out.println("用户[" + loginRequestPacket.getUserName() + "]登录失败！");
        }
        //登录成功响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ServerSessionUtil.unBindSession(ctx.channel());
    }


}
