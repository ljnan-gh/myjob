package myjob.core.db.mysql;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import myjob.common.util.EncryptorUtil;
import myjob.common.util.JsonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;

@Component
@Slf4j
public class ChangeDataSourceServiceImpl implements ChangeDataSourceService {

    /**
     * 所有的数据源配置信息，在类进行初始化的时候就加载好，暂且放在自定义缓存中
     */
    private List<DBSourceInfo> dBInfos = null;
    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Autowired
    private void setEncryptorUtil(EncryptorUtil encryptorUtil) {
        this.encryptorUtil = encryptorUtil;
    }

    private EncryptorUtil encryptorUtil;

    @Override
    public boolean changeDB(String datasourceId) {
        DynamicDataSourceContextHolder.removeDataSource();
        //获取所有数据源配置信息
        List<DBSourceInfo> dataSourceList = getdBInfos();
        if (!CollectionUtils.isEmpty(dataSourceList)) {
            for (DBSourceInfo dbinfo : dataSourceList) {
                if (dbinfo.getDatasourceId().equals(datasourceId)) {
                    System.out.println("已找到数据源，datasourceId是：" + datasourceId);
                    dynamicRoutingDataSource.checkCreateDataSource(dbinfo);
                    //切换数据源
                    DynamicDataSourceContextHolder.setDataSource(datasourceId);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取数据源列表
     */
    public List<DBSourceInfo> getdBInfos() {
        return this.dBInfos;
    }

    /**
     * 加载数据源配置
     */
    public List<DBSourceInfo> loadDBInfo() {
        ClassPathResource classPathResource = new ClassPathResource("application-dbinfo.yml");
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = classPathResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map = yaml.load(inputStream);
        map.remove("druidpool");
        for (Object value : map.values()) {
            LinkedHashMap lhm = (LinkedHashMap) value;
            lhm.put("password", encryptorUtil.decryptStr(String.valueOf(lhm.get("password"))));
        }

        //将字符串转成对象
        Type dbinfo = new TypeToken<ArrayList<DBSourceInfo>>() {
        }.getType();
        dBInfos = JsonUtil.getList2(JsonUtil.getResultJsonStr(map.values()), dbinfo);
        return dBInfos;
    }
}
