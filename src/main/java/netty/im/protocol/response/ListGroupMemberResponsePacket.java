package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;
import netty.im.session.Session;

import java.util.List;

import static netty.im.protocol.command.Command.LIST_GROUP_MEMBER_RESPONSE;

@Data
public class ListGroupMemberResponsePacket extends Packet {

    private boolean success;
    private String reason;
    private List<Session> groupMembers;
    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBER_RESPONSE;
    }
}
