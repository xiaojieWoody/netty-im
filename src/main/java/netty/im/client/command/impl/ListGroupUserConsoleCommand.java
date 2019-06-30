package netty.im.client.command.impl;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.protocol.request.ListGroupMemberRequestPacket;
import netty.im.util.ClientSessionUtil;

import java.util.Scanner;

@Slf4j
public class ListGroupUserConsoleCommand implements ConsoleCommand {

    public static final ListGroupUserConsoleCommand INSTANCE = new ListGroupUserConsoleCommand();
    private ListGroupUserConsoleCommand(){}

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入群组groupId获取群内用户：");
        String groupId = scanner.next();
        if(!ClientSessionUtil.inGroup(channel, groupId)) {
            System.out.println("您不在群组["+groupId+"]内！");
            return;
        }
        ListGroupMemberRequestPacket listGroupMemberRequestPacket = new ListGroupMemberRequestPacket();
        listGroupMemberRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMemberRequestPacket);
    }
}
