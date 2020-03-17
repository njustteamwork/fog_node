package com.njust.fog_node.controller;

import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import com.njust.fog_node.mysql.edf.EDFDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class DataController {
    @Autowired
    private EDFDaoImpl edfDao;

    @PostMapping("/postEData")
    public String postEData(@RequestParam String data){
        System.out.println(data);
        EncryptedDataForm encryptedDataForm = EncryptedDataForm.jsonToEncryptedDataForm(data);
        edfDao.addToRaw(encryptedDataForm);
        return data;
    }
}
