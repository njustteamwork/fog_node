package com.njust.fog_node.dataprocessor;

import com.njust.fog_node.paillier.PaillierCalculator;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class EDataAggregator {


    public List eDataAggregator(List<EncryptedDataForm> dataList, PaillierCalculator paillierCalculator){

        List<ResultData> resultDataList = new ArrayList<>();

        //排序
        Collections.sort(dataList, new Comparator<EncryptedDataForm>() {
            @Override
            public int compare(EncryptedDataForm data1, EncryptedDataForm data2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int flag = data1.getUsername().compareTo(data2.getUsername());
                    if (flag == 0){
                        try{
                            Date dt1 = format.parse(format.format(data1.getDate()));
                            Date dt2 = format.parse(format.format(data2.getDate()));
                            if (dt1.getTime() > dt2.getTime()) {
                                return 1;
                            } else if (dt1.getTime() < dt2.getTime()) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return flag;
            }
        });

        for(int i = 0; i < dataList.size(); i++){
            //统计每名病人数据量
            int count = 0;
            for(int j = i + 1; j < dataList.size(); j++){
                if(dataList.get(i).getUsername().equals(dataList.get(j).getUsername())){
                    count++;
                }else{ break; }
            }
            //向结果对象中添加一条新数据
            ResultData resultData = new ResultData();
            resultDataList.add(resultData);
            //设置用户名 数据量 最早最晚时间
            resultData.setUserName(dataList.get(i).getUsername());
            resultData.setDataCount(count+1);
            resultData.setEarliestDate(dataList.get(i).getDate());
            resultData.setLatestDate(dataList.get(i+count).getDate());
            resultData.setKeyTimeStamp(dataList.get(i).getKeyTimeStamp());

            BigInteger sumOfTemperature = dataList.get(i).getUserTemperature();
            BigInteger sumOfHeartRate = dataList.get(i).getUserHeartRate();
            for(int j = 0; j < count; j++){
                sumOfTemperature = paillierCalculator.add(sumOfTemperature,dataList.get(i + 1).getUserTemperature());
                sumOfHeartRate = paillierCalculator.add(sumOfHeartRate,dataList.get(i + 1).getUserHeartRate());
                dataList.remove(i + 1 );
            }
            resultDataList.get(i).setSumOfTemperature(sumOfTemperature);
            resultDataList.get(i).setSumOfHeartRate(sumOfHeartRate);
        }

        //排序结果
        for (ResultData resultData : resultDataList){
            System.out.println("username:" + resultData.getUserName() + " earliestDate:" +resultData.getEarliestDate() + " latestDate:" + resultData.getLatestDate());
            System.out.println("datacount:" + resultData.getDataCount());
            System.out.println("sumOfHeartRate:"+resultData.getSumOfHeartRate() + " sumOfTemperature:" + resultData.getSumOfTemperature());
            System.out.println("___________________________________________");
        }

        return resultDataList;
    }
}
