package netty.im.client.command.impl;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.util.SessionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
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
        log.info("ConsoleCommandManager 拿到第一个指令：{}", command);
        if (!SessionUtil.hasLogin(channel)) {
            return;
        }
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        log.info("ConsoleCommandManager 指令{}", JSON.toJSONString(consoleCommand));
        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("无法识别的指令：" + command + "，请重新输入！");
        }
    }
}
