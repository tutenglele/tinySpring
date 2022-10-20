package cn.tinyspring.springframework.test.dependence;

public class Husband {

    private Wife wife;

    public String querywife() {
        return "Husband.wife";
    }
}
