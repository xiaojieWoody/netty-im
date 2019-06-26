package netty.im.protocol.command;

public interface Command {
//    void exec(Scanner scanner, Channel channel);

    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
    byte MESSAGE_REQUEST = 3;
    byte MESSAGE_RESPONSE = 4;
}
