package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.SEDN_MESSAGE_TO_GROUP_REQUEST;

@Data
public class SendMessageToGroupRequestPacket extends Packet {

    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return SEDN_MESSAGE_TO_GROUP_REQUEST;
    }
}
