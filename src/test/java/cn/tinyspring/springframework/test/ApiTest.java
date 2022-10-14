package cn.tinyspring.springframework.test;

import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;
import cn.tinyspring.springframework.beans.factory.BeanFactory;
import cn.tinyspring.springframework.beans.factory.support.DefaultListableBeanFactory;
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
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
//        // 3.第一次获取 bean
//        UserService userService = (UserService) beanFactory.getBean("userService");
//        userService.queryUserInfo();
//        // 4.第二次获取 bean from Singleton
//        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
//        userService_singleton.queryUserInfo();
        //有参数使用
        UserService userService = (UserService) beanFactory.getBean("userService", "haha");
        userService.queryUserInfo();
    }


}
