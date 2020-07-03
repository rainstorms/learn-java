package rain.springbootproperties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @ConfigurationProperties 加在方法上
 * 通过 ApplicationStart 中 @import 注入到容器
 */
public class RainProperties2 {

    @Bean
    @ConfigurationProperties("rain.company")
    public RainProperties1 company() {
        return new RainProperties1();
    }
}
