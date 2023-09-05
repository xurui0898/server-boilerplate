package com.boilerplate.server.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Mybatis-Plus自动生成实体类
 */
public class MybatisPlusGenerator {
    //需要自动生成的表名（表名要大写，否则生成的只有文件夹没有文件）
    public static String[] TABLES = new String[]{
            "AREA_STREET",
    };
    //输出目录：直接输出到正式目录可以改为 /server-dao/src/main/java
    public static String OUTPUT_DIR = "/target/generator-code";
    //设置生成包名
    public static String PACKAGE_NAME = "com.boilerplate.server";
    //作者
    public static String AUTHOR = "流浪";


    public static void main(String[] args) {
        //构建一个代码生成器对象
        AutoGenerator mpg = new AutoGenerator();

        //1.全局配置
        GlobalConfig gc = new GlobalConfig();
        //获取当前用户项目目录
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath:==" + projectPath);
        //输出目录
        gc.setOutputDir(projectPath + OUTPUT_DIR);

        //设置作者
        gc.setAuthor(AUTHOR);
        //实体属性 Swagger2 注解
        gc.setSwagger2(false);
        //生成之后是否打开文件夹
        gc.setOpen(false);
        //是否覆盖
        gc.setFileOverride(true);
        //去Service的I前缀
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        //2.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/uyes_ptj?useUnicode=true&useSSL=false&characterEncoding=utf8&tinyInt1isBit=false");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        //自定义类型转换规则
        dsc.setTypeConvert(new MySqlTypeConvert(){
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                String t = fieldType.toLowerCase();
                //避免tinyint(1)生成为boolean
                if (t.contains("tinyint")) {
                    return DbColumnType.INTEGER;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }

        });
        mpg.setDataSource(dsc);

        //3.包的配置
        PackageConfig pc = new PackageConfig();
        //模块名字
        pc.setParent(PACKAGE_NAME);
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        //4.自定义配置 - Entity文件覆盖更新
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        cfg.setFileCreate((configBuilder, fileType, filePath) -> {
            //如果是Entity则直接返回true表示写文件
            if (fileType == FileType.ENTITY) {
                return true;
            }
            //否则先判断文件是否存在
            File file = new File(filePath);
            boolean exist = file.exists();
            if (!exist) {
                file.getParentFile().mkdirs();
            }
            //文件不存在或者全局配置的fileOverride为true才写文件
            return !exist || configBuilder.getGlobalConfig().isFileOverride();
        });
        mpg.setCfg(cfg);

        //5.策略配置
        StrategyConfig strategy = new StrategyConfig();
        //设置需要自动生产的表名，可包含多张表
        //表名要大写，否则生成的只有文件夹没有文件
        strategy.setInclude(TABLES);
        //下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //是否支持lombok
        strategy.setEntityLombokModel(true);
        //rest风格
        strategy.setRestControllerStyle(true);
        //下划线命名
        strategy.setControllerMappingHyphenStyle(true);

        //6.自动填充配置：创建时间更新时间填充
        TableFill gmt_creat = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmt_modified = new TableFill("update_time", FieldFill.UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmt_creat);
        tableFills.add(gmt_modified);
        strategy.setTableFillList(tableFills);
        mpg.setStrategy(strategy);
        //执行自动生成
        mpg.execute();
    }
}
