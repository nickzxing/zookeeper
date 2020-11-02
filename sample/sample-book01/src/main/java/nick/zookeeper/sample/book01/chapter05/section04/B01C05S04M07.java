package nick.zookeeper.sample.book01.chapter05.section04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 一个典型时间戳生成的并发问题
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class B01C05S04M07 {

    private final static Logger log = LoggerFactory.getLogger(B01C05S04M07.class);

    private final static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss|SSS");

    public static void main(String[] args) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Order No.: {}", format.format(new Date()));
            }).start();
        }
        latch.countDown();
    }
}
