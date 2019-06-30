package netty.im.protocol;


import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import netty.im.client.handler.HeartBeatTimerHandler;
import netty.im.protocol.request.*;
import netty.im.protocol.response.*;
import netty.im.serialize.Serializer;
import netty.im.serialize.impl.JSONSerializer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static netty.im.protocol.command.Command.*;


/**
 * 自定义协议 编解码
 * 魔数（4）- 版本号（1） - 序列化算法（1） - 指令（1） - 数据长度（4）- 数据（n）
 */
@Slf4j
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
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBER_REQUEST, ListGroupMemberRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBER_RESPONSE, ListGroupMemberResponsePacket.class);
        packetTypeMap.put(SEDN_MESSAGE_TO_GROUP_REQUEST, SendMessageToGroupRequestPacket.class);
        packetTypeMap.put(SEND_MESSAGE_TO_GROUP_RESPONSE, SendMessageToGroupResponsePacket.class);

        packetTypeMap.put(SINGLE_PUSH_REQUEST, ServerPushSingleMessageRequestPacket.class);
        packetTypeMap.put(SINGLE_PUSH_RESPONSE, ServerPushSingleMessageResponsePacket.class);
        packetTypeMap.put(GROUP_PUSH_REQUEST, ServerPushGroupMessageRequestPacket.class);
        packetTypeMap.put(GROUP_PUSH_RESPONSE, ServerPushGroupMessageResponsePacket.class);
        packetTypeMap.put(ALL_PUSH_REQUEST, ServerPushAllMessageRequestPacket.class);
        packetTypeMap.put(ALL_PUSH_RESPONSE, ServerPushAllMessageResponsePacket.class);

        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);

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
            log.info("PacketCodec decode............{}",JSON.toJSONString(serialize.deserialize(packetType, bytes)));
            return serialize.deserialize(packetType, bytes);
        }

        return null;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        log.info("PacketCodec encode.............{}" , JSON.toJSONString(packet));
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

//        byteBuf.writeByte(MAGIC_NUMBER);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
//        byteBuf.writeByte(bytes.length);
        byteBuf.writeInt(bytes.length);

        try {
            log.info("encode bytes..............{}", new String(bytes, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("PacketCodec encode bytes.....", e);
        }

        byteBuf.writeBytes(bytes);
    }

    private Class<? extends Packet> getPacketByCommand(byte command) {
        return packetTypeMap.get(command);
    }

    private Serializer getSerialize(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

}
