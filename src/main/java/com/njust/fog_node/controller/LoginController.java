package com.njust.fog_node.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestParam String userString) {
        /**
         * 验证用户登录信息
         */
        System.out.println(userString);
        return "success";
    }

}
