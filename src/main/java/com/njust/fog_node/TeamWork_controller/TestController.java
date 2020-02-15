package com.njust.fog_node.TeamWork_controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String test(){
        System.out.println("test work!!!");
        return "i love java";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam String params) {
        System.out.println(params);
        return "hello " + params;
    }

}

