package netty.common.protocol.request;

import lombok.Data;
import netty.common.protocol.Packet;

import static netty.common.protocol.command.Command.LOGIN_REQUEST;


@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
