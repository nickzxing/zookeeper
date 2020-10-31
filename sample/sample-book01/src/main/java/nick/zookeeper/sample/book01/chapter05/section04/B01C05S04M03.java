package nick.zookeeper.sample.book01.chapter05.section04;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用curator更新节点内容
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class B01C05S04M03 {

    private final static Logger log = LoggerFactory.getLogger(B01C05S04M03.class);

    public static void main(String[] args) throws Exception {
        String path = "/B01C05S04M03";
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
                .forPath(path, "init".getBytes());
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        log.info("Success set zookeeper node for: {}, new version: {}", path, client.setData().withVersion(stat.getVersion()).forPath(path).getVersion());
        // 使用过期的stat变量进行更新操作，抛出异常BadVersionException
        client.setData().withVersion(stat.getVersion()).forPath(path);
    }
}
