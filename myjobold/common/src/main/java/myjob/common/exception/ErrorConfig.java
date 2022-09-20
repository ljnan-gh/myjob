package myjob.common.exception;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import myjob.common.util.JsonUtil;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
//@Order
public class ErrorConfig {
    //后边存在redis中
    private Map<String, String> map = new HashMap<>();

    //    @PostConstruct
    public void loadYml() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("application-dbinfo.yml");
        Yaml yaml = new Yaml();
        InputStream inputStream = classPathResource.getInputStream();
        map = yaml.load(inputStream);
    }

    @PostConstruct
    public void loadProperties() throws IOException {
        Properties prop = new Properties();
        ClassPathResource classPathResource = new ClassPathResource("error_dictionary.properties");
        InputStream inputStream = classPathResource.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");
        prop.load(isr);
        map = JsonUtil.getMap(prop.toString());
    }


    public String getErrorMessage(String code) {
        return map.get(code);
    }
}
