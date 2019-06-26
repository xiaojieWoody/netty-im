package netty.im.client.command.impl;

import io.netty.channel.Channel;
import netty.im.client.command.ConsoleCommand;
import netty.im.util.SessionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();

        consoleCommandMap.put("sendToUser", SendToUserConsoleCommand.INSTANCE);
//        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 拿到第一个指令
        String command = scanner.next();
        if(!SessionUtil.hasLogin(channel)) {
            return;
        }
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if(consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("无法识别的指令：" + command + "，请重新输入！");
        }
    }
}
