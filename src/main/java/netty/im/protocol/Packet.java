package netty.im.protocol;

import lombok.Data;

@Data
public abstract class Packet {
//public interface Packet {

    /**
     * 版本
     */
//    byte version = 1;
    private Byte version = 1;

    /**
     * 操作命令
     * @return
     */
//    String getCommand();
    public abstract Byte getCommand();
}
