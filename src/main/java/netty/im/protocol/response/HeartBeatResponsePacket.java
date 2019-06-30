package netty.im.protocol.response;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.HEARTBEAT_RESPONSE;

@Data
public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
