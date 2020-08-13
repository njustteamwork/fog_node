package com.njust.fog_node.controller;

import com.njust.fog_node.dataprocessor.DataContainer;
import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试用controller
 */
@RestController
public class TestController {

    @Autowired
    private DataContainer dataContainer;

    @GetMapping("/hello")
    public String hello(){
        System.out.println("test work!!!");
        return "i love java";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam String params) {
        System.out.println(params);
        return "hello " + params;
    }

    @GetMapping("/test")
    public String test(){
        List<EncryptedDataForm> list = dataContainer.queryEncryptedDataForms();
        for(EncryptedDataForm encryptedDataForm : list){
            System.out.println(encryptedDataForm.getUsername());
        }
        return "i love java";
    }

}

