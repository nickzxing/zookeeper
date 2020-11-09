package nick.zookeeper.sample.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * 启动类
 *
 * @author NickZxing
 * @date 2020/11/9 19:09
 */
@EnableAutoConfiguration
public class SampleDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleDubboProviderApplication.class,args);
    }
}
