package myjob.core.db.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import lombok.extern.slf4j.Slf4j;
import myjob.common.util.EncryptorUtil;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@PropertySource(value = {"classpath:application-dbinfo.yml"})
public class DruidDbConfig {
    @Value("${Default.url}")
    private String dbUrl;
    @Value("${Default.username}")
    private String username;
    @Value("${Default.password}")
    private String password;
    @Value("${Default.driver}")
    private String driver;
    // 连接池连接信息
    @Value("${druidpool.initial-size}")
    private int initialSize;
    @Value("${druidpool.min-idle}")
    private int minIdle;
    @Value("${druidpool.max-active}")
    private int maxActive;
    @Value("${druidpool.max-wait}")
    private int maxWait;

    @Autowired
    private void setEncryptorUtil(EncryptorUtil encryptorUtil) {
        this.encryptorUtil = encryptorUtil;
    }

    private EncryptorUtil encryptorUtil;

    @Bean("mainDataSource")
    @Primary
    @Qualifier("mainDataSource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();
        // 基础连接信息
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(encryptorUtil.decryptStr(password));
        datasource.setDriverClassName(driver);
        // 连接池连接信息
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        datasource.setPoolPreparedStatements(false);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
        //申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
        datasource.setTestOnBorrow(true);
        //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        datasource.setTestWhileIdle(true);
        //用来检测连接是否有效的sql
        datasource.setValidationQuery("select 1 from dual");
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        //配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
        datasource.setMinEvictableIdleTimeMillis(180000);
        datasource.setKeepAlive(true);
        return datasource;
    }

    @Bean(name = "dynamicDataSource")
    @Qualifier("dynamicDataSource")
    public DynamicRoutingDataSource dynamicDataSource() throws SQLException {
        DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
        dynamicDataSource.setDebug(false);
        //配置缺省的数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource());
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //额外数据源配置 TargetDataSources
        targetDataSources.put("mainDataSource", dataSource());
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }


    @Bean
//    @Profile({"dev","dbinfo"}) 设置dev dbinfo环境开启，保证效率
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        //用mybatis的这里会有点区别，mybatis用的是SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        //ms 设置sql执行最大时间，如果超过则不执行
        Interceptor[] plugins = new Interceptor[]{new PaginationInterceptor(), new PerformanceInterceptor().setMaxTime(100).setFormat(true)};
        sqlSessionFactoryBean.setPlugins(plugins);

        return sqlSessionFactoryBean.getObject();
    }


    /**
     * @Description: 将动态数据加载类添加到事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicRoutingDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
