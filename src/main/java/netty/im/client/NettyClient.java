package netty.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.im.client.command.impl.ConsoleCommandManager;
import netty.im.client.command.impl.LoginConsoleCommand;
import netty.im.client.handler.IMClientHandler;
import netty.im.client.handler.LoginResponseHandler;
import netty.im.client.handler.MessageResponseHandler;
import netty.im.codec.PacketDecoder;
import netty.im.codec.PacketEncoder;
import netty.im.handler.PacketCodecHandler;
import netty.im.server.handler.AuthHandler;
import netty.im.util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static final int PORT = 8001;
    public static final String HOST = "127.0.0.1";
    public static final int MAX_RETRY = 5;

    public static void main(String[] args){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 自定义协议编解码
                        pipeline.addLast(PacketCodecHandler.INSTANCE);
                        // 登录
                        pipeline.addLast(LoginResponseHandler.INSTANCE);
                        // 登录后不在校验登录
                        pipeline.addLast(AuthHandler.INSTANCE);
                        // 消息收发逻辑
                        pipeline.addLast(IMClientHandler.INSTANCE);
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if(future.isSuccess()) {
                System.out.println("连接到服务器.....");
                Channel channel = ((ChannelFuture)future).channel();
                //启动控制台
                startConsoleThread(channel);
            } else if(retry == 0) {
                System.out.println("重试次数已用完，绑定服务器失败！");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.out.println(new Date() + "：连接失败，第" + order + "次尝试重新连接....");
                bootstrap.config().group().schedule(()->{
                    connect(bootstrap, host, port, retry-1);
                }, delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

//        new Thread(()->{
//            if(!SessionUtil.hasLogin(channel)) {
//                loginConsoleCommand.exec(scanner, channel);
//            } else {
//                consoleCommandManager.exec(scanner, channel);
//            }
//        }).start();
        new Thread(() ->{
            while (!Thread.interrupted()) {
                if(!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}
