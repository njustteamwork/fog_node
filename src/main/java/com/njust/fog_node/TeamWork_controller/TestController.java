package com.njust.fog_node.TeamWork_controller;

import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import com.njust.fog_node.mysql.edf.EDFDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private EDFDaoImpl edfDao;

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
        List<EncryptedDataForm> list = edfDao.queryEncryptedDataForms();
        for(EncryptedDataForm encryptedDataForm : list){
            System.out.println(encryptedDataForm.getUserName());
        }
        edfDao.deleteRawById(5L);
        return "i love java";
    }

}

