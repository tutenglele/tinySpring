package cn.tinyspring.springframework.test.dependence;

public class Wife {
    private Husband husband;

    private IMother mother;

    public String queryHusband() {
        return "wife.husband Mother.callMother: " + mother.callMother();
    }
}
