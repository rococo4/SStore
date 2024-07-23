package com.sstore.getwayservice.Controllers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.sstore.getwayservice.Requests.SignInRequest;
import com.sstore.getwayservice.Requests.SignUpRequest;
import com.sstore.getwayservice.Services.ApiService;
import com.sstore.getwayservice.Services.SignInUpService;
import com.sstore.getwayservice.Services.VerifyService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.apache.catalina.User;
import com.sstore.userservice.UserControllerGrpc;
import com.sstore.userservice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private final ApiService apiService;
    private final SignInUpService signInUpService;

    @PostMapping("/secure")
    public Object secure(@RequestHeader("jwt") String token,
                         @RequestHeader("name") String serviceName,
                         @RequestHeader("request-type") String requestType,
                         @RequestHeader("endpoint") String endpoint,
                         @RequestBody Object body) {

        return apiService.sendRequest(token, serviceName, requestType, endpoint, body);
    }
    @PutMapping("/insecure/sign-in")
    public String signIn(@RequestBody SignInRequest signInRequest) throws InvalidProtocolBufferException {
        var u = UserService.SignInRequest.newBuilder()
                .setPassword(signInRequest.getPassword())
                .setLogin(signInRequest.getUsername())
                .build();
        return JsonFormat.printer().print(signInUpService.signIn(u));
    }
    @PostMapping("/insecure/sign-up")
    public String signUp(@RequestBody SignUpRequest signUpRequest) throws InvalidProtocolBufferException {
        var u = UserService.SignUpRequest.newBuilder()
                .setEmail(signUpRequest.getEmail())
                .setPassword(signUpRequest.getPassword())
                .setLogin(signUpRequest.getUsername())
                .build();
        log.info("Sign up request: {}", u);
        UserService.JwtResponse jwt = signInUpService.signUp(u);
        log.info("Sign up response: {}", jwt.getJwt());
        String jsonResponse = JsonFormat.printer().print(jwt);
        return JsonFormat.printer().print(jwt);
    }

}
