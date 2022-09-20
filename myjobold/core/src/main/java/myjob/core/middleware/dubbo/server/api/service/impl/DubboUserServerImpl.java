package myjob.core.middleware.dubbo.server.api.service.impl;


import myjob.core.middleware.dubbo.server.api.service.DubboUserServer;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 提供者Project--服务实现暴露类
 */
//@Service(interfaceClass= DubboUserServer.class)
//@Component
public class DubboUserServerImpl implements DubboUserServer {

    @Override
    public String getUser(Long id) {
        String name = "";
        if(1==id){
            name = "董志峰";
        }else if(2==id){
            name = "刘亦菲";
        }else{
            name = "杨幂";
        }
        return name+"8082";
    }

}