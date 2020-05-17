package com.njust.fog_node.mysql.rd;

import com.njust.fog_node.dataprocessor.ResultData;

import java.util.List;


/**
 * ResultData的DAO接口
 */
public interface RDDao {
    void add(List<ResultData> list);
    List<ResultData> queryResultData();
    int deleteRawById(Long id);
}
