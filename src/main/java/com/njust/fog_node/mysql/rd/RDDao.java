package com.njust.fog_node.mysql.rd;

import com.njust.fog_node.dataprocessor.ResultData;

import java.util.List;

public interface RDDao {
    void add(List<ResultData> list);
}
