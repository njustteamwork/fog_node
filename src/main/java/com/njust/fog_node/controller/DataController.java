package com.njust.fog_node.controller;

import com.njust.fog_node.dataprocessor.DataContainer;
import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import com.njust.fog_node.paillier.PaillierPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
/**
 * 用于接收安卓端数据的controller
 */
public class DataController {

    @Autowired
    private DataContainer dataContainer;

    private PaillierPublicKey paillierPublicKey;

    /**
     * 接受安卓端的数据
     * @param data 安卓端数据
     * @return 密钥错误就返回WTKS，无误就返回got it
     */
    @PostMapping("/postEData")
    public String postEData(@RequestParam String data){
        if(paillierPublicKey == null)
            PaillierPublicKey.renovate();
            paillierPublicKey = PaillierPublicKey.readFromFile();
        //System.out.println(data);
        EncryptedDataForm encryptedDataForm = EncryptedDataForm.jsonToEncryptedDataForm(data);
        if(encryptedDataForm.getKeyTimeStamp()!= paillierPublicKey.getTimeStamp())
            return "WKTS";
        dataContainer.addEncryptedDataForm(encryptedDataForm);
        return "got it";
    }
}
