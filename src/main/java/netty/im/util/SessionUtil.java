package netty.im.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import netty.im.attribute.Attributes;
import netty.im.session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

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
            System.out.println("用户[" + session.getUserId() + ":" + session.getUserName() + "]退出登录！");
        }
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
