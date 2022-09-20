package myjob.core.middleware.dubbo.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

//@RestController
//@EnableDubboConfiguration
//@SpringBootApplication
public class DubboClientApplication {

    @Autowired
    private DubboUserCient dubboUserCient;

    public static void main(String[] args) {
        System.setProperty("dubbo.application.logger", "slf4j");
        SpringApplication.run(DubboClientApplication.class, args);
    }

    @GetMapping("/getuser")
    public String getUser(){
        return dubboUserCient.getUser(1L);
    }
}
