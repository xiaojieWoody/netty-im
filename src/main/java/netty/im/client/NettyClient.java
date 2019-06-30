package netty.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.im.client.command.impl.manager.ConsoleCommandManager;
import netty.im.client.command.impl.LoginConsoleCommand;
import netty.im.client.handler.IMClientHandler;
import netty.im.client.handler.HeartBeatTimerHandler;
import netty.im.client.handler.response.LogoutResponseHandler;
import netty.im.codec.Spliter;
import netty.im.handler.IMIdleStateHandler;
import netty.im.client.handler.response.LoginResponseHandler;
import netty.im.handler.PacketCodecHandler;
import netty.im.util.ClientSessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static final int PORT = 8001;
    public static final String HOST = "127.0.0.1";
    public static final int MAX_RETRY = 5;

    public static void main(String[] args){
        connectServer();
    }

    public static void connectServer() {
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
                        // 每个客户端都会有各自的各种Handler，是否单例无所谓
                        // 服务端，无状态的Handler为单例
                        ChannelPipeline pipeline = ch.pipeline();
                        // 空闲检测
//                        pipeline.addLast(IMIdleStateHandler.INSTANCE);
                        pipeline.addLast(new IMIdleStateHandler());
                        // 自定义协议校验，不能使用单例模式
                        pipeline.addLast(new Spliter());
                        // 自定义协议编解码  可替代 PacketDecoder和PacketEncoder
                        pipeline.addLast(PacketCodecHandler.INSTANCE);
                        // 登录
                        pipeline.addLast(LoginResponseHandler.INSTANCE);
                        // 退出登录
                        pipeline.addLast(LogoutResponseHandler.INSTANCE);
                        // 消息收发逻辑
                        pipeline.addLast(IMClientHandler.INSTANCE);
                        // 心跳定时检测器
                        pipeline.addLast(HeartBeatTimerHandler.INSTANCE);

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
//            if(!ServerSessionUtil.hasLogin(channel)) {
//                loginConsoleCommand.exec(scanner, channel);
//            } else {
//                consoleCommandManager.exec(scanner, channel);
//            }
//        }).start();
        new Thread(() ->{
            while (!Thread.interrupted()) {
//                if(!ServerSessionUtil.hasLogin(channel)) {
                if(!ClientSessionUtil.hasClientLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}
