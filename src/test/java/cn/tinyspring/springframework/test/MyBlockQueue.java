package cn.tinyspring.springframework.test;

/**
 * 阻塞队列(BlockingQueue)是一个支持两个附加操作的线程安全的队列
 * 两个附加的操作是：在队列为空时，获取元素的线程会等待队列变为非空；当队列满时，存储元素的线程会等待队列可用
 * @param <E>
 */
public interface MyBlockQueue<E> {
    /**
     * 向阻塞队列中插入一个元素，队列满了则阻塞
     * @param obj
     * @throws InterruptedException
     */
    void put(E obj) throws InterruptedException;

    /**
     * 从阻塞队列中获取元素，队列没有则阻塞
     * @return
     * @throws InterruptedException
     */
    E take() throws InterruptedException;
}
