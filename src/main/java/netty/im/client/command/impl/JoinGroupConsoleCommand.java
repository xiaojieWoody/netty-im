package netty.im.client.command.impl;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.protocol.request.JoinGroupRequestPacket;
import netty.im.util.ClientSessionUtil;

import java.util.Scanner;

@Slf4j
public class JoinGroupConsoleCommand implements ConsoleCommand {

    public static final JoinGroupConsoleCommand INSTANCE = new JoinGroupConsoleCommand();
    private JoinGroupConsoleCommand(){}

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入要加入群组的GroupId：");
        String groupId = scanner.next();
        if(ClientSessionUtil.inGroup(channel, groupId)) {
            System.out.println("您已在群组["+groupId+"]");
            return;
        }
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
