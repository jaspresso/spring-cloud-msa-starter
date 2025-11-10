package com.example.user_service.controller;


import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.UserService;
import com.example.user_service.vo.Greeting;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private Environment env;
    private Greeting greeting;


    public UserController(UserService userService, Environment env, Greeting greeting) {
        this.userService = userService;
        this.env = env;
        this.greeting = greeting;
    }

    @GetMapping("/health-check") // http://localhost:60000/health-check
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port"));
    }

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        log.info("users.welcome ip: {}, {}, {}, {}", request.getRemoteAddr()
                , request.getRemoteHost(), request.getRequestURI(), request.getRequestURL());

        return env.getProperty("greeting.message");
    }

    @GetMapping("/message")
    public String message(@RequestHeader("f-request") String header) {
        log.info(header);
        return "Hello World in User Service.";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request.getServerPort());


        return String.format("Hi, there. This is a message from User Service on PORT %s"
                , env.getProperty("local.server.port"));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

//    @GetMapping
//    public ResponseEntity<List<UserResponse>> findAll() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers(@RequestHeader("X-User-Email") String email,
                                          @RequestHeader("X-User-Role") String role) {
        log.info("요청자: {}, role: {}", email, role);
        return userService.getAllUsers();
    }


    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("/{email}/point")
    public ResponseEntity<Void> addPoint(@PathVariable String email, @RequestParam int amount) {
        userService.addPoint(email, amount);
        return ResponseEntity.ok().build();
    }

}
