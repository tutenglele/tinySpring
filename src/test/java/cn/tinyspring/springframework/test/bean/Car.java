package cn.tinyspring.springframework.test.bean;

import java.util.Random;

public class Car implements ICar{
    private String token;
    @Override
    public String queryCar() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "car]]]]" + token;
    }
    @Override
    public String register(String carName){
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册车辆：" + carName + "done";
    }
}
