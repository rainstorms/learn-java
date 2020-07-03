package rain.springbootproperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ConfigurationProperties 加在类上
 */
@Configuration
@ConfigurationProperties("rain.home")
@Data
public class RainProperties1 {
    String province;
    String memberNumber;
    List<String> member;
}
