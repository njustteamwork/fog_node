package com.njust.fog_node.TeamWork_controller;

import com.njust.fog_node.dataprocessor.EDataAggregator;
import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import com.njust.fog_node.mysql.edf.EDFDaoImpl;
import com.njust.fog_node.paillier.PaillierCalculator;
import com.njust.fog_node.paillier.PaillierPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration      //主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 开启定时任务
public class SaticScheduleTask {
    @Autowired
    private EDFDaoImpl edfDao;

    //添加定时任务
    @Scheduled(fixedDelay = 5000)
    private void configureTasks() {
        List<EncryptedDataForm> list = null;
        EncryptedDataForm edf = null;
        Integer count;
        for(;;){
            count = 0;
            list = edfDao.queryEncryptedDataForms();
            for(EncryptedDataForm encryptedDataForm : list){
                System.out.println(encryptedDataForm.getId());
                edf = list.get(count++);
                edfDao.deleteRawById(edf.getId());
                edf.setId(null);
                edfDao.addToUsed(edf);
            }
            EDataAggregator eda = new EDataAggregator();

            PaillierPublicKey paillierPublicKey = PaillierPublicKey.paillierJsonToPublicKey("{\"n\":7037996759611275900405487329144489085210900622405788623915340046554895678557675360099993502545810105916795350348201798995744651664108236879690390748857833,\"nSquare\":49533398388298819693190911443085500113137594389227717398938303574532356291531019850234314622241175041250992063305927006862844026670633749958420794136365527887009273250901790502746504678689585917463571409706569379921923499464969602901871572009667889989146252127852333968575165007138552703354893437794045455889,\"g\":47,\"bitLength\":512,\"timeStamp\":1580452220178}");
            PaillierCalculator paillierCalculator = new PaillierCalculator(paillierPublicKey);

            eda.eDataAggregator(list,paillierCalculator);
            if(list.size()<100){
                System.out.println("执行静态定时任务结束时间: " + LocalDateTime.now());
                break;
            }
            else System.out.println("数据过多，进行下一轮处理");
        }

    }
}