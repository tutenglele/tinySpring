package cn.tinyspring.springframework.test.bean;

public class UserService {
    private String uId;
    private UserDao userDao;
//    private String name;

//    public UserService(String name) {
//        this.name = name;
//    }
    public UserService() {
    }
    public void queryUserInfo() {
        System.out.println("查询用户信息：" + userDao.queryUserName(uId));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
//        sb.append("").append(name);
        return sb.toString();
    }
}
