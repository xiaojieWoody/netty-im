package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.HEARTBEAT_REQUEST;

@Data
public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
