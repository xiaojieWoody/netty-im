package netty.im.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@ChannelHandler.Sharable
public class IMExceptionHandler extends ChannelInboundHandlerAdapter {
    public static final IMExceptionHandler INSTANCE = new IMExceptionHandler();
    public IMExceptionHandler(){}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("报错了.........................", cause);
        System.out.println(cause.getMessage());
        ctx.channel().close();
    }
}
