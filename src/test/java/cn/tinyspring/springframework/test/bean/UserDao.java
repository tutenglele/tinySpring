package cn.tinyspring.springframework.test.bean;

import java.util.HashMap;

public class UserDao {
    private static HashMap<String, String> map = new HashMap<>();
    static {
        map.put("111", "ssss");
        map.put("112", "2222");
    }
    public String queryUserName(String uId) {
        return map.get(uId);
    }
}
