package netty.im.client.command.impl.manager;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.client.command.impl.*;

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
        consoleCommandMap.put("createGroup", CreateGroupConsoleCommand.INSTANCE);
        consoleCommandMap.put("quitGroup", QuitGroupConsoleCommand.INSTANCE);
        consoleCommandMap.put("joinGroup", JoinGroupConsoleCommand.INSTANCE);
        consoleCommandMap.put("listGroupUser", ListGroupUserConsoleCommand.INSTANCE);
        consoleCommandMap.put("sendGroup", SendMessageToGroupConsoleCommand.INSTANCE);
        consoleCommandMap.put("logout", LogoutConsoleCommand.INSTANCE);
        //登录
        consoleCommandMap.put("login", new LoginConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 拿到第一个指令
        String command = scanner.next();
        log.info("ConsoleCommandManager 拿到第一个指令：{}", command);
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        log.info("ConsoleCommandManager 指令{}", JSON.toJSONString(consoleCommand));
        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("无法识别的指令：" + command + "，请重新输入！");
        }
    }
}
