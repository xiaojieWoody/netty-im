package netty.im.protocol.request;

import lombok.Data;
import netty.im.protocol.Packet;

import java.util.List;

import static netty.im.protocol.command.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> members;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
