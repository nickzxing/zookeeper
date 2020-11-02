package nick.zookeeper.sample.book01.chapter05.section04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 使用CyclicBarrier模拟一个赛跑比赛
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class B01C05S04M10 {

    private final static Logger log = LoggerFactory.getLogger(B01C05S04M10.class);
    private static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) throws Exception {
        ExecutorService tp = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), new ThreadPoolExecutor.AbortPolicy());
        tp.submit(new Thread(() -> {
            log.info("1号选手准备好了");
            try {
                B01C05S04M10.barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("1号选手起跑");
        }));
        tp.submit(new Thread(() -> {
            log.info("2号选手准备好了");
            try {
                B01C05S04M10.barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("2号选手起跑");
        }));
        tp.submit(new Thread(() -> {
            log.info("3号选手准备好了");
            try {
                B01C05S04M10.barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("3号选手起跑");
        }));
        tp.shutdown();
    }
}
