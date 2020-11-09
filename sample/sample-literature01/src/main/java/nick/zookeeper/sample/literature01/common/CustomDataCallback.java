package nick.zookeeper.sample.literature01.common;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AsyncCallback.DataCallback
 *
 * @author NickZxing
 * @date 2020/10/29 17:52
 */
public class CustomDataCallback implements AsyncCallback.DataCallback {

    private final static Logger log = LoggerFactory.getLogger(CustomDataCallback.class);

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        log.info("Get data: {}", data);
        log.info("Stat info: {}, {}, {}", stat.getCzxid(), stat.getMzxid(), stat.getVersion());
    }
}
