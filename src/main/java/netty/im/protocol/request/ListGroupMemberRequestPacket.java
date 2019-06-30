package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.LIST_GROUP_MEMBER_REQUEST;

@Data
public class ListGroupMemberRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBER_REQUEST;
    }
}
