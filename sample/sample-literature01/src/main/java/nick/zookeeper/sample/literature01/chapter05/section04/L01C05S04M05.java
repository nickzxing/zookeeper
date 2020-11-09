package nick.zookeeper.sample.literature01.chapter05.section04;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用TreeCache
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class L01C05S04M05 {

    private final static Logger log = LoggerFactory.getLogger(L01C05S04M05.class);

    private static String path = "/L01C05S04M05";
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
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, "ccc".getBytes());
        final TreeCache cache = new TreeCache(client, path);
        cache.start();
        cache.getListenable().addListener((c, event) -> {
            switch (event.getType()) {
                case NODE_ADDED:
                    log.info("Node added, new data:{}", event.getData());
                    break;
                case NODE_UPDATED:
                    log.info("Node updated, new data:{}", event.getData());
                    break;
                case NODE_REMOVED:
                    log.info("Node removed, new data:{}", event.getData());
                    break;
                default:
                    break;
            }
        });
        client.setData().forPath(path, "ddd".getBytes());
        Thread.sleep(3000);
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path + "/abc", "eee".getBytes());
        client.delete().deletingChildrenIfNeeded().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
