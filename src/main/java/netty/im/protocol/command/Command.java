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
    byte LIST_GROUP_MEMBER_REQUEST = 11;
    byte LIST_GROUP_MEMBER_RESPONSE = 12;
    byte SEDN_MESSAGE_TO_GROUP_REQUEST = 13;
    byte SEND_MESSAGE_TO_GROUP_RESPONSE = 14;

    byte SINGLE_PUSH_REQUEST = 15;
    byte SINGLE_PUSH_RESPONSE = 16;
    byte GROUP_PUSH_REQUEST = 17;
    byte GROUP_PUSH_RESPONSE = 18;
    byte ALL_PUSH_REQUEST = 19;
    byte ALL_PUSH_RESPONSE = 20;

    byte HEARTBEAT_REQUEST = 21;
    byte HEARTBEAT_RESPONSE = 22;

    byte LOGOUT_REQUEST = 23;
    byte LOGOUT_RESPONSE = 24;

}
