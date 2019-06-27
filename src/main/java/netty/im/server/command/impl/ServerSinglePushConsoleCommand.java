package netty.im.server.command.impl;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.protocol.request.MessageRequestPacket;
import netty.im.protocol.request.ServerPushSingleMessagePacket;
import netty.im.server.command.ServerConsoleCommand;
import netty.im.util.SessionUtil;

import java.util.Scanner;

@Slf4j
public class ServerSinglePushConsoleCommand implements ServerConsoleCommand {

    public static final ServerSinglePushConsoleCommand INSTANCE = new ServerSinglePushConsoleCommand();
    private ServerSinglePushConsoleCommand() {}


    @Override
//    public void exec(Scanner scanner, String command) {
    public void exec(Scanner scanner) {
        System.out.println("【请输入用户ID和要推送的消息：】");
        String userId = scanner.next();
        String message = scanner.next();

        Channel channel = SessionUtil.getChannelByUserId(userId);
        log.info("ServerSinglePushConsoleCommand exec channel..........{}", JSON.toJSONString(channel));
        if(channel != null && SessionUtil.hasLogin(channel)) {
            ServerPushSingleMessagePacket singleMessagePacket = new ServerPushSingleMessagePacket();
            singleMessagePacket.setUserId(userId);
            singleMessagePacket.setMessage(message);
            long startTime = System.currentTimeMillis();
            log.info("ServerSinglePushConsoleCommand exec packet..........{}", JSON.toJSONString(singleMessagePacket));
            channel.writeAndFlush(singleMessagePacket).addListener(future -> {
                if (future.isDone()) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("服务端推送消息["+message+"]到[" + userId + "]成功，耗时:" + (endTime -startTime) + "毫秒");
                }
            });

        } else {
            System.err.println("用户[" + userId + "]不在线！");
        }
    }
}
