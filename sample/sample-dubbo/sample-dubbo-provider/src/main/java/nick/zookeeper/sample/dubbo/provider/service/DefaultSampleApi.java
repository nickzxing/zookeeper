package nick.zookeeper.sample.dubbo.provider.service;

import nick.zookeeper.sample.dubbo.api.SampleApi;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * 样例接口实现
 *
 * @author NickZxing
 * @date 2020/11/9 19:06
 */
@DubboService(version = "${sample.service.version}")
public class DefaultSampleApi implements SampleApi {

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String getSth(String sth) {
        return String.format("[%s]: %s", serviceName, sth);
    }
}
