package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.SINGLE_PUSH;

@Data
public class ServerPushSingleMessagePacket extends Packet {

    private String userId;
    private String message;

    @Override
    public Byte getCommand() {
        return Byte.valueOf(SINGLE_PUSH);
    }
}
