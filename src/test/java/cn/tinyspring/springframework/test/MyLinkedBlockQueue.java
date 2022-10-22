package cn.tinyspring.springframework.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyLinkedBlockQueue<E> implements MyBlockQueue<E>{
    /**
     * 队列容量
     */
    private final int capacity;

    /**
     * 当前的元素个数
     */
    private AtomicInteger count;

    /**
     * 添加元素的锁，及队列尾部的锁
     */
    private ReentrantLock putLock = new ReentrantLock();

    /**
     * 阻塞队列满时的等待队列
     */
    private Condition fullCondition = putLock.newCondition();

    /**
     * 获取元素的锁，队列头部的锁
     */
    private ReentrantLock takeLock = new ReentrantLock();

    /**
     * 阻塞队列空时的等待队列
     */
    private Condition emptyCondition = takeLock.newCondition();

    /**
     * 链表头节点，有效数据为null
     */
    private Node<E> head = new Node<>();

    /**
     * 链表最后一个结点
     */
    private Node<E> tail = head;

    public MyLinkedBlockQueue(int capacity) {
        this.capacity = capacity;
        this.count = new AtomicInteger(0);
    }

    @Override
    public void put(E obj) throws InterruptedException {
        int c = -1;
        //创建结点
        Node<E> node = new Node<>(obj);
        //加锁
        putLock.lockInterruptibly();
        try {
            //队列满了，就阻塞在这里
            while(count.get() == capacity) {
                fullCondition.await();
            }
            //将结点添加到链表里
            tail.next = node;
            tail = tail.next;
            c = count.getAndIncrement();
            //如果有线程在阻塞在获取元素上，则唤醒他
            if(c == 0) {
                signalNotEmpty();
            }
            if(count.get() < capacity) {
                fullCondition.signal();
            }
        } finally {
            putLock.unlock();
        }
    }


    @Override
    public E take() throws InterruptedException {
        int c = -1;
        E res;
        //加锁
        takeLock.lockInterruptibly();
        try {
            // 如果队列为空则阻塞
            while(count.get() == 0) {
                emptyCondition.await();
            }
            Node<E> h = head;
            Node<E> node = head.next;

            head = node;
            res = node.val;
            head.val = null;

            c = count.getAndDecrement();
            // 如果有线程正在阻塞在添加元素上，则唤醒它
            if(c == capacity) {
                signalNotFull();
            }
            if(count.get() > 0) {
                emptyCondition.signal();
            }
        }finally {
            takeLock.unlock();
        }
        return res;
    }

    /**
     * 通知获取元素的线程有新元素
     */
    private void signalNotEmpty() {
        takeLock.lock();
        try {
            //唤醒线程
            emptyCondition.signal();
        } finally {
            takeLock.unlock();
        }
    }

    private void signalNotFull() {
        putLock.lock();
        try {
            fullCondition.signal();
        } finally {
            putLock.unlock();
        }
    }

    static class Node<E> {
        E val;
        Node<E> next;

        public Node(E val) {
            this.val = val;
        }
        public Node() {

        }
    }
}
