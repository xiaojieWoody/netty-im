package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;
import netty.im.session.Session;

import java.util.List;

import static netty.im.protocol.command.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;
    private String reason;
    private String groupId;
    private String createUserId;
    private List<Session> membersInfo;
    private List<String> extra;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
