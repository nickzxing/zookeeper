package nick.zookeeper.sample.literature01.chapter05.section04;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Fluent风格的curator创建一个zk客户端
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class L01C05S04M01 {

    private final static Logger log = LoggerFactory.getLogger(L01C05S04M01.class);

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
