package rain.springbootproperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestProperties {
    @Autowired RainProperties1 rainProperties1;
    @Autowired RainProperties1 company;

    @Value("${rain.home.member}")
    private String a;

    @PostConstruct
    public void init() {
        System.out.println(rainProperties1.getMember());
        System.out.println(rainProperties1.getMemberNumber());
        System.out.println(rainProperties1.getProvince());

        System.out.println(company.getMember());
        System.out.println(company.getMemberNumber());
        System.out.println(company.getProvince());

        System.out.println(a);
    }
}
