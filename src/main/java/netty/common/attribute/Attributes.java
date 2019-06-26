package netty.common.attribute;

import io.netty.util.AttributeKey;
import netty.common.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
