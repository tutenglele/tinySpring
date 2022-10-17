package cn.tinyspring.springframework.beans.factory;

public interface DisposableBean {
    /**
     * 进行销毁操作
     * @throws Exception
     */
    void destory() throws Exception;
}
