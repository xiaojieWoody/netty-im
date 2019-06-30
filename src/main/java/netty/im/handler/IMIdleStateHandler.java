package netty.im.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import netty.im.session.Session;
import netty.im.util.ClientSessionUtil;

import java.util.concurrent.TimeUnit;

@Slf4j
@ChannelHandler.Sharable
public class IMIdleStateHandler extends IdleStateHandler {

    /**
     * 客户端和服务端都要关闭
     */
//    public static final IMIdleStateHandler INSTANCE = new IMIdleStateHandler();

    private static final int READER_IDLE_TIME = 20;

//    private IMIdleStateHandler() {
//        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
//    }

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        Session clientSession = ClientSessionUtil.getClientSession(ctx.channel());
        if (clientSession == null) {
            System.out.println("["+READER_IDLE_TIME+"]秒未读到数据，关闭["+ctx.channel().id()+"]连接！");
        } else {
            System.out.println("["+READER_IDLE_TIME+"]秒未读到数据，关闭["+clientSession.getUserId() + ":" + clientSession.getUserName()+"]连接！");
        }
        ctx.channel().close();
    }
}
