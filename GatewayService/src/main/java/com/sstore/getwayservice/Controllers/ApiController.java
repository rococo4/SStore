package com.sstore.getwayservice.Controllers;

import com.sstore.getwayservice.Requests.SignUpRequest;
import com.sstore.getwayservice.Services.ApiService;
import com.sstore.getwayservice.Services.SignInUpService;
import com.sstore.getwayservice.Services.VerifyService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.apache.catalina.User;
import org.example.userservice.UserControllerGrpc;
import org.example.userservice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

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
    public UserService.JwtResponse signIn(@RequestBody UserService.SignInRequest signInRequest) {
        return signInUpService.signIn(signInRequest);
    }
    @PostMapping("/insecure/sign-up")
    public UserService.JwtResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        var u = UserService.SignUpRequest.newBuilder()
                .setEmail(signUpRequest.getEmail())
                .setPassword(signUpRequest.getPassword())
                .setLogin(signUpRequest.getUsername())
                .build();
        return signInUpService.signUp(u);
    }

}
