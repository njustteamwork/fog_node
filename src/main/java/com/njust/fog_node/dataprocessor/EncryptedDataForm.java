package com.njust.fog_node.dataprocessor;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.Date;

public class EncryptedDataForm {
    private Long id = null;
    private Date date;
    private String username;
    private BigInteger userTemperature;
    private BigInteger userHeartRate;
    private long keyTimeStamp;

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
    public String getUsername(){
        return username;
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
    public EncryptedDataForm(Date date,String username){
        this.date = date;
        this.username = username;
    }

    public void setDate(Date date) {this.date = date;}
    public void setUserName(String username) {this.username = username;}
    public void setUserHeartRate(String userHeartRate) { this.userHeartRate = new BigInteger(userHeartRate);}
    public void setUserTemperature(String userTemperature) { this.userTemperature = new BigInteger(userTemperature);}
    public void setId(Long id){ this.id= id;}

    public long getKeyTimeStamp() {
        return keyTimeStamp;
    }

    public void setKeyTimeStamp(long keyTimeStamp) {
        this.keyTimeStamp = keyTimeStamp;
    }
}


