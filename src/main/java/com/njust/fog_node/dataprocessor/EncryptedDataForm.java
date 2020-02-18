package com.njust.fog_node.dataprocessor;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.Date;

public class EncryptedDataForm {
    private Long id = null;
    private Date date;
    private String userName;
    private BigInteger userTemperature;
    private BigInteger userHeartRate;

    public EncryptedDataForm(){}

    public String getJsonStringEDataForm(){
        Gson gson = new Gson();
        String jsonStringEDataForm = gson.toJson(this, EncryptedDataForm.class);
        return jsonStringEDataForm;
    }

    public static EncryptedDataForm jsonToEncryptedDataForm(String encryptedData){
        Gson gson = new Gson();
        return gson.fromJson(encryptedData, EncryptedDataForm.class);
    }

    //    聚合用
    public String getUserName(){
        return userName;
    }
    public BigInteger getUserTemperature() {
        return userTemperature;
    }
    public BigInteger getUserHeartRate() {
        return userHeartRate;
    }
    public Date getDate() {
        return date;
    }
    public Long getId(){return id;}

    //    测试用
    public EncryptedDataForm(Date date,String userName){
        this.date = date;
        this.userName = userName;
    }

    public void setDate(Date date) {this.date = date;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setUserHeartRate(String userHeartRate) { this.userHeartRate = new BigInteger(userHeartRate);}
    public void setUserTemperature(String userTemperature) { this.userTemperature = new BigInteger(userTemperature);}
    public void setId(Long id){ this.id= id;}
}


