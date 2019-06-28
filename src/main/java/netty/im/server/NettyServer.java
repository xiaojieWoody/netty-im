package netty.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.im.codec.Spliter;
import netty.im.handler.PacketCodecHandler;
import netty.im.server.command.impl.ServerConsoleCommandManager;
import netty.im.server.handler.AuthHandler;
import netty.im.server.handler.IMServerHandler;
import netty.im.server.handler.LoginRequestHandler;
import sun.nio.ch.ThreadPool;

import java.util.Date;
import java.util.Scanner;

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
                        // 校验是否是自定义协议
                        // 不能设置成单例模式
//                        pipeline.addLast(Spliter.INSTANCE);
                        pipeline.addLast(new Spliter());
                        // 自定义协议编解码
                        pipeline.addLast(PacketCodecHandler.INSTANCE);
                        // 登录
                        pipeline.addLast(LoginRequestHandler.INSTANCE);
                        // 登录校验
                        pipeline.addLast(AuthHandler.INSTANCE);
                        // 业务消息处理
                        pipeline.addLast(IMServerHandler.INSTANCE);
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
//                startServerConsoleThread();
            } else {
                System.out.println("服务端端口[" + port + "]绑定失败!");
            }
        });
        startServerConsoleThread();
    }

    private static void startServerConsoleThread() {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("【输入11，选择单推用户；输入12，推送某群用户；输入13，推送所有用户】");
//        String command = scanner.next();

        new Thread(()->{
            while (!Thread.interrupted()) {
//                ServerConsoleCommandManager.INSTANCE.exec(scanner, command);
                ServerConsoleCommandManager.INSTANCE.exec(scanner);
            }
        }).start();
    }
}
