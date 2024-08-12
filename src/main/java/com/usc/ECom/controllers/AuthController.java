package com.usc.ECom.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.usc.ECom.http.Response;


@RestController //default return JSON  vs. @Controller in mvc return 
public class AuthController {

    @GetMapping("/checklogin")
    public Response checkLogin(Authentication authentication) {
        return new Response(authentication != null);
    }
}
