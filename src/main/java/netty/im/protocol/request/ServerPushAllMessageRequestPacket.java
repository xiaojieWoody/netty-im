package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.ALL_PUSH_REQUEST;

@Data
public class ServerPushAllMessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return ALL_PUSH_REQUEST;
    }
}
