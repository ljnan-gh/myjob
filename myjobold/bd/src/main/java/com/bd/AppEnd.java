package com.bd;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import myjob.core.middleware.zookeeper.util.ZkApi;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppEnd implements ApplicationRunner {
    @Autowired
    ZkApi zkApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        zkApi.getChildren("/ljnan-service");
        String path = "/ljnan-service";
        String value = zkApi.getData(path,new Watcher(){
            @SneakyThrows
            public void process(WatchedEvent event) {
                List<String> a= zkApi.getChildren(path);
                if(a!= null){
                    for(String t:a){
                        log.info(t);
                    }
                }
            }
        });
    }
}
