package myjob.core.middleware.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  dubboConfig配置类代替application.properties配置类中的配置
 */
//@Configuration
public class DubboConfig {

    /**
     * 配置文件中的dubbo.application.name
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig config = new ApplicationConfig("user-service-provider");
        return  config;
    }

    /**
     * dubbo.registry.address
     * @return
     */
    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig config = new RegistryConfig("zookeeper://192.168.0.126:2181");
        return  config;
    }

    /**
     * dubbo.protocol.name \ port
     * @return
     */
    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig config = new ProtocolConfig("dubbo",20880);
        return  config;
    }

//    @Bean
//    public ServiceConfig<UserService> serviceConfigUserService(UserService userService,ApplicationConfig applicationConfig,RegistryConfig registryConfig){
//        ServiceConfig<UserService> serviceConfig = new ServiceConfig<UserService>();
//        serviceConfig.setInterface(UserService.class);
//        serviceConfig.setRef(userService);
//
//        serviceConfig.setApplication(applicationConfig);
//        serviceConfig.setRegistry(registryConfig);
//        serviceConfig.export();
//        return  serviceConfig;
//    }

}
