package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String toUserId;
    private String fromUserId;
    private String fromUserName;
    private String message;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
