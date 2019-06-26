package netty.im.serialize.impl;

import com.alibaba.fastjson.JSON;
import netty.im.serialize.SerializeAlgorithm;
import netty.im.serialize.Serializer;


public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializeAlgorithm() {
        return SerializeAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
