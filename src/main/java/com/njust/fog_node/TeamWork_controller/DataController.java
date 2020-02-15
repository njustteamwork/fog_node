package com.njust.fog_node.TeamWork_controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @PostMapping("/postEData")
    public String postEData(@RequestParam String data){
        System.out.println(data);
        return data;
    }

}
