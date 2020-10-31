package nick.zookeeper.sample.book01.chapter05.section03;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 创建一个最基本的zookeeper会话实例
 *
 * @author NickZxing
 * @date 2020/10/22 20:24
 */
public class B01C05S03M01 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(B01C05S03M01.class);

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(Constants.ZK_HOSTS, Constants.SESSION_TIMEOUT, new B01C05S03M01());
        log.info("Zk state: {}", zk.getState());
        connectedSemaphore.await();
        log.info("Zk state: {}", zk.getState());
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Receive watched event: {}", event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}
