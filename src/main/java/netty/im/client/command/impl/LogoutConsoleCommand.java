package netty.im.client.command.impl;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.protocol.request.LogoutRequestPacket;

import java.util.Scanner;

@Slf4j
public class LogoutConsoleCommand implements ConsoleCommand {

    public static final LogoutConsoleCommand INSTANCE = new LogoutConsoleCommand();
    private LogoutConsoleCommand(){}

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket requestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(requestPacket);
    }
}
