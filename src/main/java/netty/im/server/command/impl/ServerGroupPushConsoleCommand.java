package netty.im.server.command.impl;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.ServerPushGroupMessageRequestPacket;
import netty.im.server.command.ServerConsoleCommand;
import netty.im.util.ServerSessionUtil;

import java.util.Scanner;

@Slf4j
public class ServerGroupPushConsoleCommand implements ServerConsoleCommand {

    public static final ServerGroupPushConsoleCommand INSTANCE = new ServerGroupPushConsoleCommand();
    private ServerGroupPushConsoleCommand(){}

    @Override
    public void exec(Scanner scanner) {
        System.out.println("请输入需要群发的GroupId和消息，用空格分隔：");
        String groupId = scanner.next();
        String message = scanner.next();
        ChannelGroup channelGroup = ServerSessionUtil.getChannelGroup(groupId);
        if(channelGroup == null) {
            System.out.println("群组["+groupId+"]不存在！");
            return;
        }

        ServerPushGroupMessageRequestPacket serverPushGroupMessageRequestPacket = new ServerPushGroupMessageRequestPacket();
        serverPushGroupMessageRequestPacket.setGroupId(groupId);
        serverPushGroupMessageRequestPacket.setMessage(message);

        for (Channel channel : channelGroup) {
            channel.writeAndFlush(serverPushGroupMessageRequestPacket);
        }

    }
}
