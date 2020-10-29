package nick.zookeeper.sample.book01.chapter05.section02;

import nick.zookeeper.sample.book01.common.Constants;
import nick.zookeeper.sample.book01.common.CustomStringCallback;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 使用异步接口创建节点
 *
 * @author NickZxing
 * @date 2020/10/22 20:24
 */
public class B01C05S02M02 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(B01C05S02M02.class);

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(Constants.ZK_HOSTS, Constants.SESSION_TIMEOUT, new B01C05S02M02());
        log.info("Zk state: {}", zk.getState());
        connectedSemaphore.await();
        log.info("Zk state: {}", zk.getState());
        zk.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new CustomStringCallback(), "I am context.");
        // 创建重复的节点路径时，return code 等于-100
        zk.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new CustomStringCallback(), "I am context.");
        zk.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new CustomStringCallback(), "I am context.");
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Receive watched event: {}", event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}