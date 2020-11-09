package nick.zookeeper.sample.literature01.chapter05.section03;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 使用同步接口获取子节点列表
 *
 * @author NickZxing
 * @date 2020/10/28 21:55
 */
public class L01C05S03M05 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(L01C05S03M05.class);

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception {
        String path = "/L01C05S03M05";
        zk = new ZooKeeper(Constants.ZK_HOSTS, Constants.SESSION_TIMEOUT, new L01C05S03M05());
        connectedSemaphore.await();
        if (null == zk.exists(path, true)) {
            zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        List<String> childrenList = zk.getChildren(path, true);
        log.info("Get children: {}", childrenList);
        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    log.info("Get children: {}", zk.getChildren(event.getPath(), true));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
