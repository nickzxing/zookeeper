package nick.zookeeper.sample.book01.chapter05.section04;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用curator创建节点
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class B01C05S04M02 {

    private final static Logger log = LoggerFactory.getLogger(B01C05S04M02.class);

    public static void main(String[] args) throws Exception {
        String path = "/B01C05S04M02";
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(Constants.ZK_HOSTS)
                .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
                .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
                .retryPolicy(policy)
                .build();
        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, "123".getBytes());
    }
}
