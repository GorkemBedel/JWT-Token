package com.SpringSecurity.jwttoken.controller;

import com.SpringSecurity.jwttoken.dto.AuthRequest;
import com.SpringSecurity.jwttoken.dto.CreateUserRequest;
import com.SpringSecurity.jwttoken.model.User;
import com.SpringSecurity.jwttoken.service.JwtService;
import com.SpringSecurity.jwttoken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @PostMapping("/addNewUser")
    public User addUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @PostMapping("/generateToken")
    public String generateToken (@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(),authRequest.password()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.username());
        }
        log.info("Invalid username " + authRequest.username());
        throw new UsernameNotFoundException(String.format("Invalid username %s", authRequest.username()));



    }

    @GetMapping("/user")
    public String getUserString(){
        return "this is users controller";
    }

    @GetMapping("/admin")
    public String getAdminString(){
        return "this is admins controller";
    }

}
