package myjob.core.middleware.dubbo.client;

import com.alibaba.dubbo.config.annotation.Reference;
import myjob.core.middleware.dubbo.server.api.service.DubboUserServer;
import org.springframework.stereotype.Component;

/**
 * .消费者--服务客户端
 */
//@Component
public class DubboUserCient {

    @Reference(check=false)
    private DubboUserServer dubboUserServer;

    public String getUser(Long id){
        return dubboUserServer.getUser(id);
    }
}
