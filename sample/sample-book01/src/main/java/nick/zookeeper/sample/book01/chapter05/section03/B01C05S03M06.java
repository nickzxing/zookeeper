package nick.zookeeper.sample.book01.chapter05.section03;

import nick.zookeeper.sample.book01.common.Constants;
import nick.zookeeper.sample.book01.common.CustomChildren2Callback;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 使用异步接口获取子节点列表
 *
 * @author NickZxing
 * @date 2020/10/28 21:55
 */
public class B01C05S03M06 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(B01C05S03M06.class);

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception {
        String path = "/B01C05S03M06";
        zk = new ZooKeeper(Constants.ZK_HOSTS, Constants.SESSION_TIMEOUT, new B01C05S03M06());
        connectedSemaphore.await();
        if (null == zk.exists(path, true)) {
            zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.getChildren(path, true, new CustomChildren2Callback(), null);
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
                    zk.getChildren(event.getPath(), true, new CustomChildren2Callback(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
