package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;
import netty.im.session.Session;

import java.util.List;

import static netty.im.protocol.command.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {

    private String groupId;
    private List<Session> membersInfo;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
