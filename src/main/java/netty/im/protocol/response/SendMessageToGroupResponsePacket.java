package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;
import netty.im.session.Session;

import java.util.List;

import static netty.im.protocol.command.Command.SEND_MESSAGE_TO_GROUP_RESPONSE;

@Data
public class SendMessageToGroupResponsePacket extends Packet {

    private boolean success;
    private String message;
    private String groupId;
    private String sendUserId;
    private String sendUserName;
    private String reason;
    private List<Session> groupMember;

    @Override
    public Byte getCommand() {
        return SEND_MESSAGE_TO_GROUP_RESPONSE;
    }
}
