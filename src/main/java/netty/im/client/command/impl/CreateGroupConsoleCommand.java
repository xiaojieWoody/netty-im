package netty.im.client.command.impl;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.protocol.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class CreateGroupConsoleCommand implements ConsoleCommand {

    public static final CreateGroupConsoleCommand INSTANCE = new CreateGroupConsoleCommand();
    private CreateGroupConsoleCommand(){}

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("创建Group，请输入Group成员ID，使用英文逗号分隔");
        String members = scanner.next();
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        log.info("组内成员：{}",members);
        createGroupRequestPacket.setMembers(Arrays.asList(members.split(",")));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
