package cn.tinyspring.springframework.test.bean;

public class UserService {
    private String uId;
    private UserDao userDao;
    private String company;
    private String location;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
//    private String name;

//    public UserService(String name) {
//        this.name = name;
//    }
    public UserService() {
    }
    public void queryUserInfo() {
        System.out.println("查询用户信息：" + userDao.queryUserName(uId) + location + company);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
//        sb.append("").append(name);
        return sb.toString();
    }
}
