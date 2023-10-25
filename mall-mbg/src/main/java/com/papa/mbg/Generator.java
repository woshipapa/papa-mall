package com.papa.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static void main(String[] args) throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings=new ArrayList<>();
        //读取generator的配置文件
        InputStream in=Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser parser=new ConfigurationParser(warnings);
        Configuration configuration=parser.parseConfiguration(in);
        in.close();
        boolean overrite=true;
        DefaultShellCallback callback=new DefaultShellCallback(overrite);
        MyBatisGenerator generator=new MyBatisGenerator(configuration,callback,warnings);
        generator.generate(null);
        warnings.forEach(
                e -> System.out.println(e)
        );

    }

}
