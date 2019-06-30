package netty.im.client.command.impl;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.protocol.request.QuitGroupRequestPacket;
import netty.im.util.ClientSessionUtil;

import java.util.Scanner;

@Slf4j
public class QuitGroupConsoleCommand implements ConsoleCommand {

    public static final QuitGroupConsoleCommand INSTANCE = new QuitGroupConsoleCommand();
    private QuitGroupConsoleCommand(){}

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入需退出群组GroupId：");
        String groupId = scanner.next();
        if(!ClientSessionUtil.inGroup(channel, groupId)) {
            System.out.println("您还没加入群组["+groupId+"]");
            return;
        }
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();
        quitGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
