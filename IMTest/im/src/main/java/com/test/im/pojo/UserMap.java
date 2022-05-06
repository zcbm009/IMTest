package com.test.im.pojo;

import java.util.HashMap;
import java.util.Map;

public class UserMap {

    public static final Map<Long, User> USER_MAP = new HashMap<>();

    static {
        USER_MAP.put(1111L, new User(1111L, "John"));
        USER_MAP.put(1112L, new User(1112L, "Alice"));
        USER_MAP.put(1113L, new User(1113L, "DaMing"));
        USER_MAP.put(1114L, new User(1114L, "Mike"));
        USER_MAP.put(1115L, new User(1115L, "Jackson"));
    }

}
