package com.example.springsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

//    @Secured("ROLE_管理员")
    @PreAuthorize("hasAuthority('demo:update')")
    @RequestMapping("/demo")
    @ResponseBody
    public String demo(){

        return "demo";
    }
    @RequestMapping("/showLogin")
    public String showLogin(){

        return "login";
    }
    @RequestMapping("/showMain")
    public String showMain(){

        return "main";
    }
    @RequestMapping("/showFail")
    public String showFail(){

        return "fail";
    }
    @RequestMapping("/showAccessDenied")
    public String showAccessDenied(){

        return "accessdenied";
    }
}
