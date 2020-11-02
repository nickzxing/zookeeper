package nick.zookeeper.sample.book01.chapter05.section04;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用curator实现分布式master选举
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class B01C05S04M06 {

    private final static Logger log = LoggerFactory.getLogger(B01C05S04M06.class);

    private static String path = "/B01C05S04M06";
    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZK_HOSTS)
            .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
            .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        LeaderSelector selector = new LeaderSelector(client, path, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                log.info("Become a master role");
                Thread.sleep(1000);
                log.info("Complicated the master selection operation, release master power");
            }
        });
        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
        selector.close();
    }
}
