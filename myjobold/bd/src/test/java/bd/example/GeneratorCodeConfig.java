package bd.example;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;
public class GeneratorCodeConfig {
	public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入"+ tip +": ");
        System.out.println(help.toString());
        if(scanner.hasNext()){
            String ipt = scanner.next();
            if(StringUtils.isNotEmpty(ipt)){
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip +"!");
    }
    public static void main(String args[]) {
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Ljnan");
        gc.setServiceName("%Service");
        gc.setOpen(false);
        gc.setSwagger2(false);
        mpg.setGlobalConfig(gc);


        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://106.128.17.74:3306/tttt?serverTimezone=UTC&autoReconnect=true");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);


        //包配置
        PackageConfig pc  = new PackageConfig();
        pc.setParent("com.bd");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
//        pc.setXml(projectPath + "/src/main/resources/mapping");
        mpg.setPackageInfo(pc);


        //配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategy.setRestControllerStyle(false);//不生成controller
        strategy.setEntityLombokModel(true);
        strategy.setInclude(scanner("表名，多个英文逗号分隔").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "-");
        mpg.setStrategy(strategy);
        mpg.setTemplate(templateConfig);
        mpg.execute();
    }
}
