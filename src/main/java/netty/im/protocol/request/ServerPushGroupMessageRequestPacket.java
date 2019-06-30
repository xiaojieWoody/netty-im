package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.GROUP_PUSH_REQUEST;

@Data
public class ServerPushGroupMessageRequestPacket extends Packet {

    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_PUSH_REQUEST;
    }
}
