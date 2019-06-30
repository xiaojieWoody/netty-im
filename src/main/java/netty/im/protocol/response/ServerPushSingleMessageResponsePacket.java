package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.SINGLE_PUSH_RESPONSE;

@Data
public class ServerPushSingleMessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return SINGLE_PUSH_RESPONSE;
    }
}
