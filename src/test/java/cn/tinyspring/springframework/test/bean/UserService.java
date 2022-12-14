package cn.tinyspring.springframework.test.bean;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.*;
import cn.tinyspring.springframework.beans.factory.annotation.Autowired;
import cn.tinyspring.springframework.beans.factory.annotation.Qualifier;
import cn.tinyspring.springframework.beans.factory.annotation.Value;
import cn.tinyspring.springframework.context.ApplicationContext;
import cn.tinyspring.springframework.context.ApplicationContextAware;
import cn.tinyspring.springframework.stereotype.Component;

@Component
public class UserService implements InitializingBean, DisposableBean, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, ApplicationContextAware {
    @Value("${token}")
    private String uId;
    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;
    private String company;
    private String location;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

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
        System.out.println("查询用户信息：" + uId + userDao + location + company);
    }


    @Override
    public void destory() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行：UserService.afterPropertiesSet");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader：" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("beanName : " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
