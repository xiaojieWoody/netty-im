package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;
import netty.im.session.Session;

import java.util.List;

import static netty.im.protocol.command.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends Packet {

    private String groupId;
    private String userId;
    private String userName;
    private List<Session> groupMemebers;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
