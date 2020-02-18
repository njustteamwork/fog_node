package com.njust.fog_node.mysql.edf;

import com.njust.fog_node.dataprocessor.EncryptedDataForm;

import java.util.List;

public interface EDFDao {
    int addToRaw(EncryptedDataForm encryptedDataForm);
    int addToUsed(EncryptedDataForm encryptedDataForm);
    int deleteRawById(Long id);
    List<EncryptedDataForm> queryEncryptedDataForms();
}
