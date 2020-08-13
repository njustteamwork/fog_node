package com.njust.fog_node.dataprocessor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class DataContainer {
    private Queue<EncryptedDataForm> eDQueue;
    private Queue<ResultData> rDQueue;
    private int eDQueryCapacity;
    private int rDQueryCapacity;

    public DataContainer(){
        eDQueue =  new ConcurrentLinkedDeque<>();
        rDQueue = new ConcurrentLinkedDeque<>();
        eDQueryCapacity = 100;
        rDQueryCapacity = 20;
    }

    public void addEncryptedDataForm(EncryptedDataForm encryptedDataForm){
        eDQueue.add(encryptedDataForm);
    }

    public List<EncryptedDataForm> queryEncryptedDataForms() {
        int lastCount = eDQueryCapacity;
        List<EncryptedDataForm> eDList = new ArrayList<>(eDQueryCapacity);
        while (lastCount>0){
            EncryptedDataForm ed = eDQueue.poll();
            if(ed!=null){
                eDList.add(ed);
                lastCount--;
            }else break;
        }
        return eDList;
    }

    public void addResultData(List<ResultData> list) {
        rDQueue.addAll(list);
    }

    public List<ResultData> queryResultData() {
        int lastCount = rDQueryCapacity;
        List<ResultData> rdList = new ArrayList<>(rDQueryCapacity);
        while (lastCount>0){
            ResultData rd = rDQueue.poll();
            if(rd!=null){
                rdList.add(rd);
                lastCount--;
            }else break;
        }
        return rdList;
    }
}
