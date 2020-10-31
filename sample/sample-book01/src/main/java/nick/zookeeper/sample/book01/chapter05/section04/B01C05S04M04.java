package nick.zookeeper.sample.book01.chapter05.section04;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用curator的异步接口
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class B01C05S04M04 {

    private final static Logger log = LoggerFactory.getLogger(B01C05S04M04.class);

    private static String path = "/B01C05S04M04";
    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZK_HOSTS)
            .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
            .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init".getBytes());
        final NodeCache cache = new NodeCache(client, path, false);
    }
}
