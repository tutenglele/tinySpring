package cn.tinyspring.springframework.test.bean;

import cn.tinyspring.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserDao {
    private static HashMap<String, String> map = new HashMap<>();
    public void initDataMethod() {
        System.out.println("执行：init-method");
        map.put("111", "ssss");
        map.put("112", "2222");
    }
    public String queryUserName(String uId) {
        return map.get(uId);
    }
    public void destroyDataMethod(){
        System.out.println("执行：destroy-method");
        map.clear();
    }
}
