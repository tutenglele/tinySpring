package cn.tinyspring.springframework.test;

import java.util.HashSet;
import java.util.Set;

public class MyThreadPool {
    //默认线程池的数量
    private static final int WORK_NUM = 32;
    //默认阻塞队列的大小
    private static final int QUEUE_SIZE = 1024;
    //线程数
    private final int threadCount;
    //阻塞队列的大小
    private final int queueSize;

    //阻塞队列
    private MyBlockQueue<Runnable> blockQueue;

    /**
     * 线程集合
     */
    private Set<WorkThread> threads;

    public MyThreadPool(int threadCount, int queueSize) {
        this.threadCount = threadCount;
        this.queueSize = queueSize;
        blockQueue = new MyLinkedBlockQueue<>(this.queueSize);
        threads = new HashSet<>(this.threadCount);
        //初始化线程
        for (int i = 0; i < threadCount; i++) {
            WorkThread thread = new WorkThread("Thread" + i);
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * 向线程池提交任务
     * @param task
     */
    public void exeute(Runnable task) {
        try {
            blockQueue.put(task);
        } catch (InterruptedException e) {
            System.out.println("提交任务被中断");
        }
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        for (WorkThread thread : threads) {
            thread.close();
            thread = null;
        }
        threads.clear();
    }

    public MyThreadPool() {
        this(WORK_NUM, QUEUE_SIZE);
    }

    /**
     * 工作线程
     */
    class WorkThread extends Thread {
        public WorkThread(String name) {
           super(name);
        }
        @Override
        public void run() {
            //不断地向阻塞队列获取任务后执行
            while (!isInterrupted()) {
                try {
                    Runnable take = blockQueue.take();
                    if(take != null) {
                        take.run();
                    }
                    take = null;
                } catch (InterruptedException e) {
                    close();
                    System.out.println("获取任务被中断");
                }
            }
        }
        public void close() {
            this.interrupt();
        }
    }
}
