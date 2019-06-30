package netty.im.server.command.impl;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.ServerPushAllMessageRequestPacket;
import netty.im.server.command.ServerConsoleCommand;
import netty.im.util.ServerSessionUtil;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class ServerAllPushConsoleCommand implements ServerConsoleCommand {

    public static final ServerAllPushConsoleCommand INSTANCE = new ServerAllPushConsoleCommand();
    private ServerAllPushConsoleCommand(){}

    @Override
    public void exec(Scanner scanner) {
        System.out.println("请输入要推送的消息:");
        String message = scanner.next();

        ServerPushAllMessageRequestPacket serverPushAllMessageRequestPacket = new ServerPushAllMessageRequestPacket();
        serverPushAllMessageRequestPacket.setMessage(message);

        List<Channel> allChannel = ServerSessionUtil.getAllChannel();
        if(allChannel.size() == 0) {
            System.out.println("没有用户在线！");
            return;
        }
        for (Channel channel : allChannel) {
            channel.writeAndFlush(serverPushAllMessageRequestPacket);
        }
    }
}
