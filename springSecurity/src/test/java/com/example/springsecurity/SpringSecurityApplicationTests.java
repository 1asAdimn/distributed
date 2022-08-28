package com.example.springsecurity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = SpringSecurityApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
class SpringSecurityApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder  encoder = new BCryptPasswordEncoder();

        String result = encoder.encode("12345");
        System.out.println("开始！");
        System.out.println(result);

        boolean flag = encoder.matches("pwd",result);
        System.out.println(flag);

        System.out.println("结束");

    }

}
