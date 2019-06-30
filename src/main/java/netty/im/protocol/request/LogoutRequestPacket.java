package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import static netty.im.protocol.command.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
