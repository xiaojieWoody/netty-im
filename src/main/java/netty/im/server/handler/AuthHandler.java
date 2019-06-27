package netty.im.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import netty.im.util.SessionUtil;

@Slf4j
@ChannelHandler.Sharable
//public class AuthHandler extends SimpleChannelInboundHandler<Packet> {
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();
    private AuthHandler() {}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("AuthHandler .......{}", JSON.toJSONString(msg));
        if(SessionUtil.hasLogin(ctx.channel())) {
//            ctx.channel().pipeline().remove(LoginRequestHandler.class);
            ctx.channel().pipeline().remove(this);
            System.out.println("AuthHandler.................." + SessionUtil.getSession(ctx.channel()).getUserName());
            //
            super.channelRead(ctx, msg);
        } else {
            ctx.channel().close();
        }
    }

//    @Override
//    protected void channelRead(ChannelHandlerContext ctx, Packet msg) throws Exception {
//        if(SessionUtil.hasLogin(ctx.channel())) {
////            ctx.channel().pipeline().remove(LoginRequestHandler.class);
//            ctx.channel().pipeline().remove(this);
//            //
//            super.channelRead(ctx, msg);
//        } else {
//            ctx.channel().close();
//        }
//    }
}
