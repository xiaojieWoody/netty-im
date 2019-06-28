package netty.im.protocol.command;

public interface Command {
//    void exec(Scanner scanner, Channel channel);

    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
    byte MESSAGE_REQUEST = 3;
    byte MESSAGE_RESPONSE = 4;
    byte CREATE_GROUP_REQUEST = 5;
    byte CREATE_GROUP_RESPONSE = 6;
    byte QUIT_GROUP_REQUEST = 7;
    byte QUIT_GROUP_RESPONSE = 8;
    byte JOIN_GROUP_REQUEST = 9;
    byte JOIN_GROUP_RESPONSE = 10;

    String SINGLE_PUSH = "11";
    String GROUP_PUSH = "12";
    String ALL_PUSH = "13";

}
