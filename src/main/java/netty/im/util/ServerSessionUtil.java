package netty.im.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import netty.im.attribute.Attributes;
import netty.im.session.Session;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerSessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

//    public static void unBindSession(String userId) {
//        if (hasLogin(userId)) {
//            userIdChannelMap.get(userId).attr(Attributes.SESSION).set(null);
//            userIdChannelMap.remove(userId);
//        }
//    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
//            Session session = channel.attr(Attributes.SESSION).get();
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static List<Session> getGroupMember(String groupId) {
        List<Session> result = new ArrayList<>();
        ChannelGroup group = getChannelGroup(groupId);
        if (group != null) {
            for (Channel channel : group) {
                result.add(getSession(channel));
            }
        }
        return result;
    }

    public static void removeGroup(String groupId) {
        groupIdChannelGroupMap.get(groupId).close();
        groupIdChannelGroupMap.remove(groupId);
    }

    public static Channel getChannelByUserId(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static boolean hasLogin(Channel channel) {
        return getSession(channel) != null;
    }

//    private static boolean hasLogin(String userId) {
//        return getSession(userId) == null;
//    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

//    private static Session getSession(String userId) {
//        return userIdChannelMap.get(userId).attr(Attributes.SESSION).get();
//    }

    public static void bindGroupChannel(String groupId, ChannelGroup channelGroup) {
//        if (groupIdChannelGroupMap.get(groupId) != null) {
//            groupIdChannelGroupMap.get(groupId).add(channelGroup);
//        }
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }

    public static void unBindGroupChannel(String groupId, Channel channel) {
        if (getChannelGroup(groupId) != null) {
            getChannelGroup(groupId).remove(channel);
        }
    }

    public static List<ChannelGroup> getAllChannelGroup() {
        return new ArrayList<>(groupIdChannelGroupMap.values());
    }

    public static List<Channel> getAllChannel() {
        return new ArrayList<>(userIdChannelMap.values());
    }

    public static List<String> listGroupIdByChannel(Channel channel) {
        List<ChannelGroup> allChannelGroup = getAllChannelGroup();
        List<String> result = new ArrayList<>();
        for (ChannelGroup group : allChannelGroup) {
            for (Channel ch : group) {
                if(ch == channel) {
                    result.add(getGroupIdByGroupChannel(group));
                }
            }
        }
        return result;
    }

    public static String getGroupIdByGroupChannel(ChannelGroup channelGroup) {
        Iterator<Map.Entry<String, ChannelGroup>> iterator = groupIdChannelGroupMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ChannelGroup> next = iterator.next();
            if (next.getValue() == channelGroup) {
                return next.getKey();
            }
        }
        return null;
    }

    public static boolean hasGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId) != null;
    }

    public static List<Session> allGroupUser(String groupId) {
        List<Session> result = new ArrayList<>();
        ChannelGroup channelGroup = groupIdChannelGroupMap.get(groupId);
        for (Channel channel : channelGroup) {
            result.add(getSession(channel));
        }
        return result;
    }
}
