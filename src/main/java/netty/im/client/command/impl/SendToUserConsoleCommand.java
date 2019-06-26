package netty.im.client.command.impl;

import io.netty.channel.Channel;
import netty.im.client.command.ConsoleCommand;
import netty.im.protocol.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {

    public static final SendToUserConsoleCommand INSTANCE = new SendToUserConsoleCommand();
    private SendToUserConsoleCommand(){}

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入消息接收者及消息，中间空格分隔：");
        String toUserId = scanner.next();
        String message = scanner.next();
//        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
//        messageRequestPacket.setMessage(message);
//        messageRequestPacket.setToUserId(toUserId);
//        channel.writeAndFlush(messageRequestPacket);
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
