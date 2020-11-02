package nick.zookeeper.sample.book01.chapter05.section04;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 使用curator实现分布式锁功能
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class B01C05S04M08 {

    private final static Logger log = LoggerFactory.getLogger(B01C05S04M08.class);

    private static String path = "/B01C05S04M08";
    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZK_HOSTS)
            .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
            .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();
    private final static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss|SSS");

    public static void main(String[] args) throws Exception {
        client.start();
        final InterProcessMutex mutex = new InterProcessMutex(client, path);
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    mutex.acquire();
                    log.info("Order No.: {}", format.format(new Date()));
                    mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        latch.countDown();
    }
}
