package nick.zookeeper.sample.book01.chapter05.section050301;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 创建一个最基本的zookeeper会话实例
 *
 * @author NickZxing
 * @date 2020/10/22 20:24
 */
public class B01C05S050301M01 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(B01C05S050301M01.class);

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(Constants.ZK_HOSTS, 5000, new B01C05S050301M01());
            log.info("ZooKeeper state: {}", zooKeeper.getState());
            connectedSemaphore.await();
            log.info("ZooKeeper state: {}", zooKeeper.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Receive watched event: {}", event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}