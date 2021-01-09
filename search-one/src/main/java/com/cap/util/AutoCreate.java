package com.cap.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

public class AutoCreate {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ": ");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "!");
    }

    public static void main(String[] args) {
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/search-one/src/main/java");//设置代码的生成路径
        gc.setFileOverride(true);//是否覆盖以前的文件
        gc.setOpen(true);//是否打开生成目录
        gc.setAuthor("zjx");//设置项目作者名称
        gc.setIdType(IdType.AUTO);//设置主键策略
        gc.setBaseResultMap(true);//生成基本ResultMap
        gc.setBaseColumnList(true);//生成基本ColumnList
        gc.setServiceName("%sService");//去掉服务器默认前缀
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/searchmore?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("zjx347320623.");
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.cap");
        pc.setMapper("mapper");
        pc.setXml("mapper.xml");
        pc.setEntity("pojo");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        //策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        sc.setColumnNaming(NamingStrategy.underline_to_camel);//数据库字段映射到实体变量的命名策略
        sc.setEntityLombokModel(true);
        sc.setRestControllerStyle(true);
        sc.setControllerMappingHyphenStyle(true);
        //sc.setTablePrefix("");
        sc.setInclude(scanner("表名，多个英文逗号分割").split(","));
        mpg.setStrategy(sc);

        //生成代码
        mpg.execute();
    }
}
