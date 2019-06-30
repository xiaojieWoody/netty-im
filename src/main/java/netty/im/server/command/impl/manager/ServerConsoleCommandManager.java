package netty.im.server.command.impl.manager;

import lombok.extern.slf4j.Slf4j;
import netty.im.server.command.ServerConsoleCommand;
import netty.im.server.command.impl.ServerAllPushConsoleCommand;
import netty.im.server.command.impl.ServerGroupPushConsoleCommand;
import netty.im.server.command.impl.ServerSinglePushConsoleCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class ServerConsoleCommandManager implements ServerConsoleCommand {

    public static final ServerConsoleCommandManager INSTANCE = new ServerConsoleCommandManager();
    private Map<String, ServerConsoleCommand> serverConsoleCommandMap;
    private ServerConsoleCommandManager(){
        serverConsoleCommandMap = new HashMap<>();

        serverConsoleCommandMap.put("pushSingle", ServerSinglePushConsoleCommand.INSTANCE);
        serverConsoleCommandMap.put("pushGroup", ServerGroupPushConsoleCommand.INSTANCE);
        serverConsoleCommandMap.put("pushAll", ServerAllPushConsoleCommand.INSTANCE);
//        serverConsoleCommandMap.put("11", ServerSinglePushConsoleCommand.INSTANCE);
    }

    @Override
//    public void exec(Scanner scanner, String command) {
    public void exec(Scanner scanner) {
//        System.out.println("【输入11，选择单推用户；输入12，推送某群用户；输入13，推送所有用户】");
        String command = scanner.next();
        log.info("ServerConsoleCommandManager exec......指令：{}", command);
        ServerConsoleCommand serverConsoleCommand = serverConsoleCommandMap.get(command);
        if(serverConsoleCommand == null){
            System.out.println("不识别[" + command +"]指令！");
            return;
        }
//        serverConsoleCommand.exec(scanner, command);
        serverConsoleCommand.exec(scanner);
    }
}
