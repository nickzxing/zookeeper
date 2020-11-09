package nick.zookeeper.sample.literature01.chapter05.section04;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用curator实现分布式barrier
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class L01C05S04M11 {

    private final static Logger log = LoggerFactory.getLogger(L01C05S04M11.class);

    private static String path = "/L01C05S04M11";
    private static DistributedBarrier barrier;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                CuratorFramework client = CuratorFrameworkFactory.builder()
                        .connectString(Constants.ZK_HOSTS)
                        .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
                        .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
                        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                        .build();
                client.start();
                barrier = new DistributedBarrier(client, path);
                log.info("{}号barrier设置", Thread.currentThread().getName());
                try {
                    barrier.setBarrier();
                    barrier.waitOnBarrier();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("启动...");
            }).start();
        }
        Thread.sleep(2000);
        barrier.removeBarrier();
    }
}
