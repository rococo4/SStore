package com.sstore.getwayservice;

import com.sstore.userservice.ControllerImpl.UsersController;
import com.sstore.userservice.UserService;
import com.sstore.userservice.UserServiceApplication;
import com.sstore.userservice.store.Repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

}
