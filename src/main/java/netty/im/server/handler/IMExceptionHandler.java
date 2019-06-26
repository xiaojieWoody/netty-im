package netty.im.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class IMExceptionHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(IMExceptionHandler.class);
    public static final IMExceptionHandler INSTANCE = new IMExceptionHandler();
    public IMExceptionHandler(){}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("报错了.........................", cause);
        System.out.println(cause.getMessage());
        ctx.channel().close();
    }
}
