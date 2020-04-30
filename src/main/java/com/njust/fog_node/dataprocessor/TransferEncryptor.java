package com.njust.fog_node.dataprocessor;
import java.security.interfaces.DSAPublicKey;

public interface TransferEncryptor {
    //TODO 产生一个新密钥
    DSAPublicKey generateKey();

    //TODO 将密钥以文件方式保存
    void saveToFile();

    //TODO 从文件中读取密钥
    DSAPublicKey readFromFile();

    //TODO 加密json字符串
    Byte[] encrypt(String jsonString);

    //TODO 解密json字符串
    String decrypt(Byte[] bytes);
}
