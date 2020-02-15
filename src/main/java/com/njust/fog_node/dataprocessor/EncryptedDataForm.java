package com.njust.fog_node.dataprocessor;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.Date;

public class EncryptedDataForm {
    private Date date;
    private String username;
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

    //    测试用
    public EncryptedDataForm(Date date,String username){
        this.date = date;
        this.username = username;
    }

    public void setUserHeartRate(String userHeartRate) {
        this.userHeartRate = new BigInteger(userHeartRate);
    }

    public void setUserTemperature(String userTemperature) {
        this.userTemperature = new BigInteger(userTemperature);
    }
}


