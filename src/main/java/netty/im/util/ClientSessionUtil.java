package netty.im.util;

import io.netty.channel.Channel;
import netty.im.attribute.Attributes;
import netty.im.session.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    //    private static final Map<String, ChannelGroup> groupIdChanneGroupMap = new ConcurrentHashMap<>();
    private static final Map<String, List<Session>> groupIdChanneGroupMap = new ConcurrentHashMap<>();

    public static void bindClientSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindClientSession(Channel channel) {
        if (hasClientLogin(channel)) {
//            channel.attr(Attributes.SESSION).set(null);
//            userIdChannelMap.remove(getClientSession(channel).getUserId());
            Session session = getClientSession(channel);
            userIdChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static void bindClientGroup(Channel channel, String groupId, List<Session> session) {
        if (hasClientLogin(channel)) {
            groupIdChanneGroupMap.put(groupId, session);
        }
    }

    public static boolean quitClientGroup(Channel channel, String userId, String groupId) {
        if (hasClientLogin(channel) && hasGroup(groupId)) {
            List<Session> sessions = listGroupUsers(channel, groupId);
            Iterator<Session> iterator = sessions.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getUserId().equals(userId)) {
                    iterator.remove();
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static boolean inGroup(Channel channel, String groupId) {
        if (hasGroup(groupId)) {
            return groupIdChanneGroupMap.get(groupId).contains(getClientSession(channel));
        }
        return false;
    }

    public static boolean joinClientGroup(Channel channel, Session session, String groupId) {
        if (hasClientLogin(channel) && hasGroup(groupId)) {
            List<Session> sessions = listGroupUsers(channel, groupId);
            sessions.add(session);
        } else {
            return false;
        }
        return true;
    }

    public static boolean hasGroup(String groupId) {
        return groupIdChanneGroupMap.get(groupId) != null;
    }

    public static void unBindClientGroup(String groupId) {
        groupIdChanneGroupMap.remove(groupId);
    }

    public static Session getClientSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static boolean hasClientLogin(Channel channel) {
        return getClientSession(channel) != null;
    }

    public static List<Session> listGroupUsers(Channel channel, String groupId) {
        List<Session> result = new ArrayList<>();
        if (hasClientLogin(channel)) {
            result = groupIdChanneGroupMap.get(groupId);
        }
        return result;
    }

    public static List<String> listGroupId(Channel channel) {
        List<String> result = new ArrayList<>();
        if (hasClientLogin(channel)) {
            Iterator<Map.Entry<String, List<Session>>> iterator = groupIdChanneGroupMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Session>> next = iterator.next();
                result.add(next.getKey());
            }
        }
        return result;
    }

    public static boolean isActive(Channel channel) {
        return channel.isActive();
    }
}
