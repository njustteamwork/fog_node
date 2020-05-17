package com.njust.fog_node;

import com.google.gson.Gson;
import com.njust.fog_node.dataprocessor.EDataAggregator;
import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import com.njust.fog_node.dataprocessor.ResultData;
import com.njust.fog_node.mysql.edf.EDFDaoImpl;
import com.njust.fog_node.mysql.rd.RDDaoImpl;
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
    private EDFDaoImpl edfDao;

    @Autowired
    private RDDaoImpl rdDao;

    //添加定时任务
    //每5秒执行一次聚合任务
    @Scheduled(fixedDelay = 5000)
    private void aggregatorTask(){
        List<EncryptedDataForm> list;

        while(true){
            list = edfDao.queryEncryptedDataForms();
            if(list.size()==0) break;
            for(EncryptedDataForm encryptedDataForm : list){
                edfDao.deleteRawById(encryptedDataForm.getId());
                encryptedDataForm.setId(null);
                edfDao.addToUsed(encryptedDataForm);
            }
            EDataAggregator eda = new EDataAggregator();
            //PaillierPublicKey paillierPublicKey = PaillierPublicKey.paillierJsonToPublicKey("{\"n\":7037996759611275900405487329144489085210900622405788623915340046554895678557675360099993502545810105916795350348201798995744651664108236879690390748857833,\"nSquare\":49533398388298819693190911443085500113137594389227717398938303574532356291531019850234314622241175041250992063305927006862844026670633749958420794136365527887009273250901790502746504678689585917463571409706569379921923499464969602901871572009667889989146252127852333968575165007138552703354893437794045455889,\"g\":47,\"bitLength\":512,\"timeStamp\":1580452220178}");
            PaillierPublicKey paillierPublicKey = PaillierPublicKey.readFromFile();
            PaillierCalculator paillierCalculator = new PaillierCalculator(paillierPublicKey);
            if(paillierPublicKey.isTimeUp()){
                System.out.println("聚合时检测到密钥过期，将重置密钥。");
                PaillierPublicKey.renovate();
                paillierPublicKey = PaillierPublicKey.readFromFile();
                paillierCalculator = new PaillierCalculator(paillierPublicKey);
            }

            rdDao.add(eda.eDataAggregator(list,paillierCalculator));

            if(list.size()<100){
                System.out.println("执行静态定时聚合任务结束时间: " + LocalDateTime.now());
                break;
            }
            else System.out.println("数据过多，处理完成并进行下一轮处理");
        }

    }


    //每五秒执行一次发送任务
    @Scheduled(fixedDelay = 5000)
    private void sendTask(){

        Gson gson = new Gson();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/data/postRData";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        List<ResultData> list = rdDao.queryResultData();
        if(list.size()==0) return;
        while(true){
            for(ResultData resultData : list){
                paramMap.add("data",gson.toJson(resultData));
                String response = restTemplate.postForObject(url,paramMap,String.class);
                System.out.println("云端返回消息："+response);
                if(response.equals("WKTS")){
                    System.out.println("密钥过期，重置密钥");
                    PaillierPublicKey.renovate();
                }
                paramMap.clear();
                rdDao.deleteRawById(resultData.getId());
            }
            if(list.size()<100){
                System.out.println("执行静态定时发送任务结束时间: " + LocalDateTime.now());
                break;
            }
        }

    }

}