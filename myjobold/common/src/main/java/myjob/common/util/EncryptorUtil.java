package myjob.common.util;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class EncryptorUtil {
    @Value("${encryptor.enpd}")
    private String password;
    @Value("${encryptor.algorithm}")
    private String algorithm;

    /**
     * 加密
     *
     * @param str
     * @return
     */
    public String encryptStr(String str) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setAlgorithm(algorithm);//"PBEWithMD5AndDES"
        standardPBEStringEncryptor.setPassword(password);//"yx"
        //加密
        str = standardPBEStringEncryptor.encrypt(str);
        return str;
    }

    public String decryptStr(String str) {
        if (str == null) {
            return "";
        }
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setAlgorithm(algorithm);//"PBEWithMD5AndDES"
        standardPBEStringEncryptor.setPassword(password);//"huaxia"
        //解密
        try {
            str = standardPBEStringEncryptor.decrypt(str.trim());
            log.info("解密成功：{}", str);
        } catch (Exception e) {
            log.info("解密失败，使用原数据：{}", str);
        }
        return str;
    }
}
