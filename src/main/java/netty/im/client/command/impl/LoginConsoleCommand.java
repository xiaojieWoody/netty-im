package netty.im.client.command.impl;


import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.command.ConsoleCommand;
import netty.im.protocol.request.LoginRequestPacket;
import netty.im.util.ClientSessionUtil;

import java.util.Scanner;
@Slf4j
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.println("输入用户登录名：");
        String userName = scanner.next();
        loginRequestPacket.setUserName(userName);
        loginRequestPacket.setPassword("pwd");
        log.info("LoginConsoleCommand {}", JSON.toJSONString(loginRequestPacket));
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
