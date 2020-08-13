package com.njust.fog_node;

import com.google.gson.Gson;
import com.njust.fog_node.dataprocessor.DataContainer;
import com.njust.fog_node.dataprocessor.EDataAggregator;
import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import com.njust.fog_node.dataprocessor.ResultData;
import com.njust.fog_node.paillier.PaillierCalculator;
import com.njust.fog_node.paillier.PaillierPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Configuration      //主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 开启定时任务
public class SaticScheduleTask {

    @Autowired
    DataContainer dataContainer;

    //添加定时任务
    //每5秒执行一次聚合任务
    @Scheduled(fixedDelay = 5000)
    private void aggregatorTask(){
        List<EncryptedDataForm> list;

        while(true){
            list = dataContainer.queryEncryptedDataForms();
            if(list.size()==0) break;
            int listSize = list.size();
            for(EncryptedDataForm encryptedDataForm : list){
                encryptedDataForm.setId(null);
            }
            EDataAggregator eda = new EDataAggregator();
            PaillierPublicKey paillierPublicKey = PaillierPublicKey.readFromFile();
            PaillierCalculator paillierCalculator = new PaillierCalculator(paillierPublicKey);
            if(paillierPublicKey.isTimeUp()){
                System.out.println("聚合时检测到密钥过期，将重置密钥。");
                PaillierPublicKey.renovate();
                paillierPublicKey = PaillierPublicKey.readFromFile();
                paillierCalculator = new PaillierCalculator(paillierPublicKey);
            }

            dataContainer.addResultData(eda.eDataAggregator(list,paillierCalculator));

            System.out.println("执行静态定时聚合任务结束时间: " + LocalDateTime.now());
            System.out.println("聚合数量：" + listSize);
            if(listSize<100) break;
            else System.out.println("数据数量超过阈值，处理完成并进行下一轮处理");
        }

    }


    //每五秒执行一次发送任务
    @Scheduled(fixedDelay = 5000)
    private void sendTask(){

        Gson gson = new Gson();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/data/postRData";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        while(true){
            List<ResultData> list = dataContainer.queryResultData();

            if(list.size()==0) return;
            for(ResultData resultData : list){
                paramMap.add("data",gson.toJson(resultData));
                String response = restTemplate.postForObject(url,paramMap,String.class);
                System.out.println("云端返回消息："+response);
                if(response.equals("WKTS")){
                    System.out.println("密钥过期，重置密钥");
                    PaillierPublicKey.renovate();
                }
                paramMap.clear();
            }
            if(list.size()<20){
                System.out.println("执行静态定时发送任务结束时间: " + LocalDateTime.now());
                break;
            }
        }

    }

}