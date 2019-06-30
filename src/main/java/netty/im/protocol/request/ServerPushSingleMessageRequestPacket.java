package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.SINGLE_PUSH_REQUEST;

@Data
public class ServerPushSingleMessageRequestPacket extends Packet {

    private String userId;
    private String message;

    @Override
    public Byte getCommand() {
        return SINGLE_PUSH_REQUEST;
    }
}
