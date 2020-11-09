package nick.zookeeper.sample.literature01.chapter05.section04;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用curator实现分布式计数器
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class L01C05S04M09 {

    private final static Logger log = LoggerFactory.getLogger(L01C05S04M09.class);

    private static String path = "/L01C05S04M09";
    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZK_HOSTS)
            .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
            .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        DistributedAtomicInteger integer = new DistributedAtomicInteger(client, path, new RetryNTimes(3, 1000));
        for (int i = 0; i < 5000; i++) {
            AtomicValue<Integer> value = integer.add(1);
            log.info("Count: {}", value.preValue());
        }
    }
}
