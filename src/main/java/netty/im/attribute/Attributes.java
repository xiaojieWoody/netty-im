package netty.im.attribute;

import io.netty.util.AttributeKey;
import netty.im.session.Session;

public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
