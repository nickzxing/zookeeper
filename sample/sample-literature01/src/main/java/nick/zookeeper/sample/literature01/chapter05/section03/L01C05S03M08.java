package nick.zookeeper.sample.literature01.chapter05.section03;

import nick.zookeeper.sample.literature01.common.Constants;
import nick.zookeeper.sample.literature01.common.CustomDataCallback;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 使用异步接口获取节点数据内容
 *
 * @author NickZxing
 * @date 2020/10/28 21:55
 */
public class L01C05S03M08 implements Watcher {

    private final static Logger log = LoggerFactory.getLogger(L01C05S03M08.class);

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception {
        String path = "/L01C05S03M08";
        zk = new ZooKeeper(Constants.ZK_HOSTS, Constants.SESSION_TIMEOUT, new L01C05S03M08());
        connectedSemaphore.await();
        if (null == zk.exists(path, true)) {
            zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        zk.getData(path, true, new CustomDataCallback(), null);
        zk.setData(path, "123".getBytes(), -1);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                try {
                    zk.getData(event.getPath(), true, new CustomDataCallback(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
