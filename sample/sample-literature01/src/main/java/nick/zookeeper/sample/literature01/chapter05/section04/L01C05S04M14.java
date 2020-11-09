package nick.zookeeper.sample.literature01.chapter05.section04;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 使用ZkPaths
 *
 * @author NickZxing
 * @date 2020/10/30 9:26
 */
public class L01C05S04M14 {

    private final static Logger log = LoggerFactory.getLogger(L01C05S04M14.class);

    private static String path = "/L01C05S04M14";
    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZK_HOSTS)
            .sessionTimeoutMs(Constants.SESSION_TIMEOUT)
            .connectionTimeoutMs(Constants.CONNECT_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        ZooKeeper zk = client.getZookeeperClient().getZooKeeper();
        log.info(ZKPaths.fixForNamespace(path, "/sub"));
        log.info(ZKPaths.makePath(path, "/sub"));
        log.info(ZKPaths.getNodeFromPath(path + "/sub1"));
        ZKPaths.PathAndNode node = ZKPaths.getPathAndNode(path + "/sub1");
        log.info(node.getPath());
        log.info(node.getNode());
        String dir1 = path + "/child1";
        String dir2 = path + "/child2";
        ZKPaths.mkdirs(zk, dir1);
        ZKPaths.mkdirs(zk, dir2);
        List<String> childrenList = ZKPaths.getSortedChildren(zk, path);
        log.info("{}", childrenList);
        ZKPaths.deleteChildren(zk, path, true);
    }
}
