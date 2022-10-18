package cn.tinyspring.springframework.test;

import cn.hutool.core.io.IoUtil;
import cn.tinyspring.springframework.aop.AdvisedSupport;
import cn.tinyspring.springframework.aop.MethodMatcher;
import cn.tinyspring.springframework.aop.TargetSource;
import cn.tinyspring.springframework.aop.aspectj.AspectJExpressionPointcut;
import cn.tinyspring.springframework.aop.framework.Cglib2AopProxy;
import cn.tinyspring.springframework.aop.framework.JdkDynamicAopProxy;
import cn.tinyspring.springframework.aop.framework.ReflectiveMethodInvocation;
import cn.tinyspring.springframework.beans.PropertyValue;
import cn.tinyspring.springframework.beans.PropertyValues;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;
import cn.tinyspring.springframework.beans.factory.BeanFactory;
import cn.tinyspring.springframework.beans.factory.config.BeanReference;
import cn.tinyspring.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.tinyspring.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import cn.tinyspring.springframework.context.support.ClassPathXmlApplicationContext;
import cn.tinyspring.springframework.core.io.DefaultResourceLoader;
import cn.tinyspring.springframework.core.io.Resource;
import cn.tinyspring.springframework.test.bean.*;
import cn.tinyspring.springframework.test.common.MyBeanFactoryPostProcessor;
import cn.tinyspring.springframework.test.common.MyBeanPostProcessor;
import cn.tinyspring.springframework.test.event.CustomEvent;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
    private DefaultResourceLoader resourceLoader;

    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }
    @Test
    public void test_classpath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }
    @Test
    public void test_file() throws IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }
    @Test
    public void test_url() throws IOException {
        // 网络原因可能导致GitHub不能读取，可以放到自己的Gitee仓库。读取后可以从内容中搜索关键字；OLpj9823dZ
        Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/blob/main/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }
    @Test
    public void test_xml() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition("classpath:spring.xml");
        // 3. 获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();

    }
    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor(){
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition("classpath:spring.xml");

        // 3. BeanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean实例化之后，修改 Bean 属性信息
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // 5. 获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }
    @Test
    public void test_context() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        // 2. 获取Bean对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void test_init_destory() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
//        System.out.println("测试结果：" + result);
    }
    @Test
    public void test_aware() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
        System.out.println("applicationContextAware为" + userService.getApplicationContext());
        System.out.println("Beanfactory为" + userService.getBeanFactory());
    }

    @Test
    public void test_prototype() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserService userService01 = applicationContext.getBean("userService", UserService.class);
        UserService userService02 = applicationContext.getBean("userService", UserService.class);

        // 3. 配置 scope="prototype/singleton"
        System.out.println(userService01);
        System.out.println(userService02);

//        // 4. 打印十六进制哈希
//        System.out.println(userService01 + " 十六进制哈希：" + Integer.toHexString(userService01.hashCode()));
//        System.out.println(ClassLayout.parseInstance(userService01).toPrintable());
    }

    @Test
    public void test_factory_bean() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2. 调用代理方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 333444L, "done!!"));
        applicationContext.registerShutdownHook();
    }

//    public void test_proxy_method() {
//        Object targetObj = new UserService();
//        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
//            MethodMatcher methodMatcher = (MethodMatcher) new AspectJExpressionPointcut("execution(* cn.tinyspring.springframework.test.bean.IUserService.*(..))");
//
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                if(methodMatcher.matches(method, targetObj.getClass())) {
//                    MethodInterceptor methodInterceptor = invocation -> {
//                      long start = System.currentTimeMillis();
//                      try {
//                          return invocation.proceed();
//                      } finally {
//                          System.out.println("监控 - Begin By AOP");
//                          System.out.println("方法名称：" + invocation.getMethod().getName());
//                          System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
//                          System.out.println("监控 - End\r\n");
//                      }
//                    };
//                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
//                }
//                return method.invoke(targetObj, args);
//            }
//        });
//        String result = proxy.queryUserInfo();
//        System.out.println("测试结果：" + result);
//    }

    @Test
    public void test_aop_expression() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* cn.tinyspring.springframework.test.bean.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        //获取自身声明的方法，getDeclaredMethod
        Method method = clazz.getDeclaredMethod("queryUserInfo");
        //获取所有public方法 getMethod
        Method method1 = clazz.getMethod("queryUserInfo");
        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
        System.out.println(pointcut.matches(method1, clazz));
    }

    @Test
    public void test_dynamic() {
        ICar car = new Car();
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(car));
        advisedSupport.setMethodInterceptor(new CarInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* cn.tinyspring.springframework.test.bean.ICar.*(..))"));

        ICar proxy = (ICar) new JdkDynamicAopProxy(advisedSupport).getProxy();
        System.out.println(proxy.queryCar());

        ICar proxy1 = (ICar) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println(proxy1.register("helgfd"));
    }

    @Test
    public void test_aop() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        ICar car = applicationContext.getBean("car", ICar.class);
//        ((Car) car).queryCar();
        System.out.println(applicationContext.getBean("car").getClass());
        System.out.println("测试结果：" + car.queryCar());
    }
    @Test
    public void test_test() {
        System.out.println(Object.class);
    }
}
