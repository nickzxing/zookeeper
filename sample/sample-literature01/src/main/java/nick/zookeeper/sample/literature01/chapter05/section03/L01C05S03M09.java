package nick.zookeeper.sample.literature01.chapter05.section03;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 访问含权限信息的数据节点
 *
 * @author NickZxing
 * @date 2020/10/22 20:24
 */
public class L01C05S03M09 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(L01C05S03M09.class);

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        String path = "/L01C05S03M09";
        ZooKeeper zk1 = new ZooKeeper(Constants.ZK_HOSTS, Constants.SESSION_TIMEOUT, new L01C05S03M09());
        connectedSemaphore.await();
        zk1.addAuthInfo("digest", "foo:true".getBytes());
        if (null == zk1.exists(path, true)) {
            zk1.create(path, "123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }
        ZooKeeper zk2 = new ZooKeeper(Constants.ZK_HOSTS, Constants.SESSION_TIMEOUT, new L01C05S03M09());
        zk2.addAuthInfo("digest", "foo:true".getBytes());
        zk2.getData(path, false, null);
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Receive watched event: {}", event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}
