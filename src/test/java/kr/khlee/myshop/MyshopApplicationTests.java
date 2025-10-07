package kr.khlee.myshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;


@EnableScheduling
@SpringBootTest
class MyshopApplicationTests {

    @Test
    void contextLoads() {
    }

}
