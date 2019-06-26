package netty.im.server;



import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.im.handler.PacketCodecHandler;
import netty.im.server.handler.LoginRequestHandler;

import java.util.Date;

public class NettyServer {

    private static final int PORT = 8001;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workderGroup = new NioEventLoopGroup();

//        ServerBootstrap serverBootstrap = new ServerBootstrap();
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workderGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
//                .childHandler(new ChannelInitializer<SocketChannel>() {
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(PacketCodecHandler.INSTANCE);
                        pipeline.addLast(LoginRequestHandler.INSTANCE);
//                        pipeline.addLast(IMExceptionHandler.INSTANCE);
                    }
                });
        bind(serverBootstrap, PORT);
    }

    //    private static void bind(ServerBootstrap serverBootstrap, int port) {
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + "服务端端口[" + port + "]绑定成功！");
            } else {
                System.out.println("服务端端口[" + port + "]绑定失败!");
            }
        });
    }
}
