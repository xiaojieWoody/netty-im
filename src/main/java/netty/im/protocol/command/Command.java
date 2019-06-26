package netty.im.protocol.command;

public interface Command {
//    void exec(Scanner scanner, Channel channel);

    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
}
