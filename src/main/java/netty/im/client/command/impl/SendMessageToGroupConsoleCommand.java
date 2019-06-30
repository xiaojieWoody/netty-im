package netty.im.client.command.impl;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.protocol.request.SendMessageToGroupRequestPacket;
import netty.im.util.ClientSessionUtil;

import java.util.Scanner;

@Slf4j
public class SendMessageToGroupConsoleCommand implements ConsoleCommand {

    public static final SendMessageToGroupConsoleCommand INSTANCE = new SendMessageToGroupConsoleCommand();
    private SendMessageToGroupConsoleCommand(){}

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("群发消息到群组,请输入群组GroupId和消息，中间空格分隔：");
        String groupId = scanner.next();
        String message = scanner.next();

        if(ClientSessionUtil.inGroup(channel, groupId)) {
            System.out.println("您不在群组["+groupId+"]中");
            return;
        }

        SendMessageToGroupRequestPacket sendMessageToGroupRequestPacket = new SendMessageToGroupRequestPacket();
        sendMessageToGroupRequestPacket.setGroupId(groupId);
        sendMessageToGroupRequestPacket.setMessage(message);

        channel.writeAndFlush(sendMessageToGroupRequestPacket);
    }
}
