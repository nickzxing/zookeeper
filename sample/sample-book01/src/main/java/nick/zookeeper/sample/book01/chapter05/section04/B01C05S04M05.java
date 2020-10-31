package nick.zookeeper.sample.book01.chapter05.section04;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用NodeCache
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class B01C05S04M05 {

    private final static Logger log = LoggerFactory.getLogger(B01C05S04M05.class);

    private static String path = "/B01C05S04M04";
    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZK_HOSTS)
            .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
            .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(Constants.ZK_HOSTS)
                .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
                .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
                .retryPolicy(policy)
                .build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
