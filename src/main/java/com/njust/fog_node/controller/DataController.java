package com.njust.fog_node.controller;

import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import com.njust.fog_node.mysql.edf.EDFDaoImpl;
import com.njust.fog_node.paillier.PaillierPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class DataController {
    public DataController(){
    }

    @Autowired
    private EDFDaoImpl edfDao;

    private PaillierPublicKey paillierPublicKey = PaillierPublicKey.readFromFile();

    @PostMapping("/postEData")
    public String postEData(@RequestParam String data){
        System.out.println(data);
        EncryptedDataForm encryptedDataForm = EncryptedDataForm.jsonToEncryptedDataForm(data);
        if(encryptedDataForm.getKeyTimeStamp()!= paillierPublicKey.getTimeStamp())
            return "WKTS";
        edfDao.addToRaw(encryptedDataForm);
        return "got it";
    }
}
