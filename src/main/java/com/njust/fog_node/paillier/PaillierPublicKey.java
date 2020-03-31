package com.njust.fog_node.paillier;

import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

public class PaillierPublicKey {
    private BigInteger n;
    private BigInteger nSquare;
    private BigInteger g;
    private int bitLength;
    private String userName;
    private Long timeStamp;

    public PaillierPublicKey(BigInteger n, BigInteger g, int bitLength){
        this.n = n;
        nSquare = n.multiply(n);
        this.g = g;
        this.bitLength = bitLength;
    }

    public BigInteger getN() {return n;}
    public BigInteger getNSquare(){return nSquare;}
    public BigInteger getG(){return g;}
    public int getBitLength(){return bitLength;}
    public Long getTimeStamp(){ return timeStamp; }
    public String getUserName(){return userName;}

    public String getJsonStringPublicKey(){
        Gson gson = new Gson();
        String jsonStringPublicKey = gson.toJson(this);
        //System.out.println(jsonStringPublicKey);
        return jsonStringPublicKey;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }


    public static PaillierPublicKey paillierJsonToPublicKey(String paillierPublicKey){
        Gson gson = new Gson();
        return gson.fromJson(paillierPublicKey,PaillierPublicKey.class);
    }

    public boolean isTimeUp(){
        Long currentTime = System.currentTimeMillis();
        if(currentTime-timeStamp>86400000)
            return true;
        return false;
    }

    public boolean saveToFile() throws Exception {
        try {
            String jsonPublicKey = this.getJsonStringPublicKey();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("PAILLIER_PUBLIC_KEY_FILE"));
            oos.writeObject(jsonPublicKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static PaillierPublicKey readFromFile() throws Exception{
        Gson gson = new Gson();
        String jsonPublicKey;
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PAILLIER_PUBLIC_KEY_FILE"));
           jsonPublicKey = ois.readObject().toString();
            PaillierPublicKey paillierPublicKey = gson.fromJson(jsonPublicKey, PaillierPublicKey.class);
            return paillierPublicKey;
        }catch (Exception e){
            System.out.println("未找到密钥文件，将从云服务器获取密钥");
            jsonPublicKey = PaillierPublicKey.renovate();
            PaillierPublicKey paillierPublicKey = gson.fromJson(jsonPublicKey, PaillierPublicKey.class);
            return paillierPublicKey;
        }
    }

    public static String renovate(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/data/getPublicKey";
        try {
            String jsonPublicKey = restTemplate.postForObject(url,null,String.class);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("PAILLIER_PUBLIC_KEY_FILE"));
            oos.writeObject(jsonPublicKey);
            return jsonPublicKey;
        } catch (Exception e) {
            System.out.println("密钥刷新失败！");
            return "false";
        }
    }

}
