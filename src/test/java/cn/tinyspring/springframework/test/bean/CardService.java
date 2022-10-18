package cn.tinyspring.springframework.test.bean;

import cn.tinyspring.springframework.stereotype.Component;

@Component("cardService")
public class CardService {
    private String token;

    @Override
    public String toString() {
        return "CardService{" +
                "token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
