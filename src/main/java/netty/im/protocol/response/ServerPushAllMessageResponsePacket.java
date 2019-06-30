package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.ALL_PUSH_RESPONSE;

@Data
public class ServerPushAllMessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return ALL_PUSH_RESPONSE;
    }
}
