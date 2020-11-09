package nick.zookeeper.sample.literature01.common;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * AsyncCallback.Children2Callback
 *
 * @author NickZxing
 * @date 2020/10/26 16:47
 */
public class CustomChildren2Callback implements AsyncCallback.Children2Callback {

    private final static Logger log = LoggerFactory.getLogger(CustomChildren2Callback.class);

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        log.info("Get children : {}", children);
    }
}
