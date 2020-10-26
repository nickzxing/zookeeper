package nick.zookeeper.sample.book01.chapter05.section050301;

import nick.zookeeper.sample.book01.common.Constants;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 创建一个最基本的zookeeper会话实例，并复用sessionId和sessionPassword
 *
 * @author NickZxing
 * @date 2020/10/22 20:24
 */
public class B01C05S050301M02 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(B01C05S050301M02.class);

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(Constants.ZK_HOSTS, 5000, new B01C05S050301M02());
            log.info("ZooKeeper state: {}", zooKeeper.getState());
            Thread.sleep(1000);
            log.info("ZooKeeper state: {}", zooKeeper.getState());
            long sessionId = zooKeeper.getSessionId();
            byte[] sessionPassword = zooKeeper.getSessionPasswd();
            // 通过sessionId和sessionPassword复用zookeeper会话实例
            zooKeeper = new ZooKeeper(Constants.ZK_HOSTS, 5000, new B01C05S050301M02(), sessionId, sessionPassword);
            log.info("ZooKeeper state: {}", zooKeeper.getState());
            Thread.sleep(1000);
            log.info("ZooKeeper state: {}", zooKeeper.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Receive watched event: {}", event);
    }
}
