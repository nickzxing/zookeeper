package nick.zookeeper.sample.dubbo.consumer;

import nick.zookeeper.sample.dubbo.api.SampleApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 启动类
 *
 * @author NickZxing
 * @date 2020/11/9 19:09
 */
@EnableAutoConfiguration
public class SampleDubboConsumerApplication {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @DubboReference(version = "${sample.service.version}")
    private SampleApi sampleApi;

    public static void main(String[] args) {
        SpringApplication.run(SampleDubboConsumerApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> logger.info(sampleApi.getSth("SampleApi.getSth"));
    }
}
