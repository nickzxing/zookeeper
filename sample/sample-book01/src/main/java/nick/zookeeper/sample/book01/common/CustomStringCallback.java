package nick.zookeeper.sample.book01.common;

import org.apache.zookeeper.AsyncCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AsyncCallback.StringCallback
 *
 * @author NickZxing
 * @date 2020/10/26 16:47
 */
public class CustomStringCallback implements AsyncCallback.StringCallback {

    private final static Logger log = LoggerFactory.getLogger(CustomStringCallback.class);

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        log.info("Create path result: {}, {}, {}, real path name:{}", rc, path, ctx, name);
    }
}
