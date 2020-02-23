package com.njust.fog_node.mysql.rd;

import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import com.njust.fog_node.dataprocessor.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RDDaoImpl implements RDDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(List<ResultData> list) {
        for(ResultData rd : list){
            jdbcTemplate.update("insert into result_data(username,earliest_date,latest_date,sum_of_temperature,sum_of_heart_rate,data_count) values(?, ?, ?, ?, ?, ?)",rd.getUserName(),rd.getEarliestDate(),rd.getLatestDate(),rd.getSumOfTemperature(),rd.getSumOfHeartRate(),rd.getDataCount());
        }
    }


    public List<ResultData> queryResultData() {
        List<ResultData> list = jdbcTemplate.query("select * from result_data order by id desc limit 20",new BeanPropertyRowMapper<ResultData>(ResultData.class));
        return list;
    }

    public int deleteRawById(Long id) {
        return jdbcTemplate.update("delete from result_data where id = ?",id);
    }
}
