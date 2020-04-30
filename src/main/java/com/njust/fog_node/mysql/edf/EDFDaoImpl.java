package com.njust.fog_node.mysql.edf;

import com.njust.fog_node.dataprocessor.EncryptedDataForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EDFDaoImpl implements EDFDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addToRaw(EncryptedDataForm encryptedDataForm) {
        return jdbcTemplate.update("insert into raw_encrypted_data(username, date,user_temperature,user_heart_rate,key_time_stamp) values(?, ?, ?, ?, ?)",encryptedDataForm.getUsername(),encryptedDataForm.getDate(),encryptedDataForm.getUserTemperature(),encryptedDataForm.getUserHeartRate(),encryptedDataForm.getKeyTimeStamp());
    }

    @Override
    public int addToUsed(EncryptedDataForm encryptedDataForm) {
        return jdbcTemplate.update("insert into used_encrypted_data(username, date,user_temperature,user_heart_rate,key_time_stamp) values(?, ?, ?, ?, ?)",encryptedDataForm.getUsername(),encryptedDataForm.getDate(),encryptedDataForm.getUserTemperature(),encryptedDataForm.getUserHeartRate(),encryptedDataForm.getKeyTimeStamp());
    }

    @Override
    public int deleteRawById(Long id) {
        return jdbcTemplate.update("delete from raw_encrypted_data where id = ?",id);
    }


    @Override
    public List<EncryptedDataForm> queryEncryptedDataForms() {
        List<EncryptedDataForm> list = jdbcTemplate.query("select * from raw_encrypted_data order by id limit 100",new BeanPropertyRowMapper<EncryptedDataForm>(EncryptedDataForm.class));
        return list;
    }
}
