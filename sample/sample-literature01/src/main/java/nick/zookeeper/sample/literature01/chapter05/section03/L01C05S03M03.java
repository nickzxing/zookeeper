package nick.zookeeper.sample.literature01.chapter05.section03;

import nick.zookeeper.sample.literature01.common.Constants;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 使用同步接口创建节点
 *
 * @author NickZxing
 * @date 2020/10/22 20:24
 */
public class L01C05S03M03 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(L01C05S03M03.class);

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(Constants.ZK_HOSTS, Constants.SESSION_TIMEOUT, new L01C05S03M03());
        log.info("Zk state: {}", zk.getState());
        connectedSemaphore.await();
        log.info("Zk state: {}", zk.getState());
        String node1 = zk.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        log.info("Success create zk node: {}", node1);
        String node2 = zk.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("Success create zk node: {}", node2);
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Receive watched event: {}", event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}
