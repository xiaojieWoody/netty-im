package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.GROUP_PUSH_RESPONSE;

@Data
public class ServerPushGroupMessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_PUSH_RESPONSE;
    }
}
