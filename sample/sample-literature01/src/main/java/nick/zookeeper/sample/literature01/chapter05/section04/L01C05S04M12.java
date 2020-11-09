package nick.zookeeper.sample.literature01.chapter05.section04;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用curator实现类似JDK中CyclicBarrier的分布式barrier
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class L01C05S04M12 {

    private final static Logger log = LoggerFactory.getLogger(L01C05S04M12.class);

    private static String path = "/L01C05S04M12";

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    CuratorFramework client = CuratorFrameworkFactory.builder()
                            .connectString(Constants.ZK_HOSTS)
                            .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
                            .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
                            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                            .build();
                    client.start();
                    DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, path, 5);
                    Thread.sleep(2000);
                    barrier.enter();
                    log.info("{}号进入barrier", Thread.currentThread().getName());
                    Thread.sleep(2000);
                    barrier.leave();
                    log.info("{}号退出barrier", Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
