package netty.im.protocol;


import io.netty.buffer.ByteBuf;
import netty.im.protocol.request.LoginRequestPacket;
import netty.im.protocol.request.MessageRequestPacket;
import netty.im.protocol.response.LoginResponsePacket;
import netty.im.protocol.response.MessageResponsePacket;
import netty.im.serialize.Serializer;
import netty.im.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static netty.im.protocol.command.Command.*;


/**
 * 自定义协议 编解码
 * 魔数（4）- 版本号（1） - 序列化算法（1） - 指令（1） - 数据长度（4）- 数据（n）
 */
public class PacketCodec {
    public static final PacketCodec INSTANCE = new PacketCodec();

    public static final int MAGIC_NUMBER = 0x12345678;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        // 不添加会报 io.netty.handler.codec.DecoderException: java.lang.NullPointerException: element
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        JSONSerializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializeAlgorithm(), serializer);
    }

    public Packet decode(ByteBuf byteBuf) {
        // 魔数
        byteBuf.skipBytes(4);
        // 版本号
//        byte version = byteBuf.readByte();
        byteBuf.skipBytes(1);
        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据长度
        int length = byteBuf.readInt();
        // 数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> packetType = getPacketByCommand(command);
        Serializer serialize = getSerialize(serializeAlgorithm);

        if (packetType != null && serialize != null) {
            return serialize.deserialize(packetType, bytes);
        }

        return null;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

//        byteBuf.writeByte(MAGIC_NUMBER);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
//        byteBuf.writeByte(bytes.length);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    private Class<? extends Packet> getPacketByCommand(byte command) {
        return packetTypeMap.get(command);
    }

    private Serializer getSerialize(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

}
