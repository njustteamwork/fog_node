package com.njust.fog_node.dataprocessor;

import java.math.BigInteger;
import java.util.Date;

/**
 * 聚合后的数据
 */
public class ResultData {
    Long id = null;
    private String username;
    private Date earliestDate;
    private Date latestDate;
    private BigInteger sumOfTemperature;
    private BigInteger sumOfHeartRate;
    private int dataCount;
    private long keyTimeStamp;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public void setUserName(String username){
        this.username = username;
    }

    public String getUserName(){
        return username;
    }

    public void setEarliestDate(Date earliestDate){
        this.earliestDate = earliestDate;
    }

    public Date getEarliestDate(){
        return earliestDate;
    }

    public void setLatestDate(Date latestDate){
        this.latestDate = latestDate;
    }

    public Date getLatestDate(){
        return latestDate;
    }

    public void setSumOfTemperature(BigInteger sumOfTemperature){
        this.sumOfTemperature = sumOfTemperature;
    }

    public BigInteger getSumOfTemperature() {
        return sumOfTemperature;
    }

    public void setSumOfHeartRate(BigInteger sumOfHeartRate) {
        this.sumOfHeartRate = sumOfHeartRate;
    }

    public BigInteger getSumOfHeartRate() {
        return sumOfHeartRate;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getDataCount() {
        return dataCount;
    }

    public long getKeyTimeStamp() {
        return keyTimeStamp;
    }

    public void setKeyTimeStamp(long keyTimeStamp) {
        this.keyTimeStamp = keyTimeStamp;
    }
}
