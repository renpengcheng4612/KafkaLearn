package cn.pc.sz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// @ComponentScan(basePackages = "cn.pc.sz.*")
public class SpringCommonKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCommonKafkaApplication.class, args);
    }
}
