package cn.tinyspring.springframework.test;

import cn.tinyspring.springframework.beans.PropertyValue;
import cn.tinyspring.springframework.beans.PropertyValues;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;
import cn.tinyspring.springframework.beans.factory.BeanFactory;
import cn.tinyspring.springframework.beans.factory.config.BeanReference;
import cn.tinyspring.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.tinyspring.springframework.test.bean.UserDao;
import cn.tinyspring.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {
//    @Test
//    public void test_BeanFactory() {
////        //1.初始化BeanFactory
////        BeanFactory beanFactory = new BeanFactory();
////        //2.注册Bean
////        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
////        beanFactory.registerBeanDefinition("userService", beanDefinition);
////        //3.获取Bean
////        UserService userService = (UserService) beanFactory.getBean("userService");
////        userService.queryUserInfo();
//    }
    @Test
    public void test_BeanFactory(){
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注册 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserDao.class);
        beanFactory.registerBeanDefinition("userDao", beanDefinition);

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", 111));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
        BeanDefinition beanDefinition1 = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition1);
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        userService1.queryUserInfo();
//        // 3.第一次获取 bean
//        UserService userService = (UserService) beanFactory.getBean("userService");
//        userService.queryUserInfo();
//        // 4.第二次获取 bean from Singleton
//        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
//        userService_singleton.queryUserInfo();
        //有参数使用
//        UserService userService = (UserService) beanFactory.getBean("userService", "haha");
//        userService.queryUserInfo();
    }


}
