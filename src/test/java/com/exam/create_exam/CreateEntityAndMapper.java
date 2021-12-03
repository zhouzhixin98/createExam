//package com.exam.create_exam;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class CreateEntityAndMapper {
//    public static String Schemas = "exam_test"; // 数据库
//    public static String Root = "root"; // 用户名
//    public static String Pwd = "root"; // 密码
//    public static String Blog = "bean"; // 包名
//    public static String parent = "com.exam.create_exam"; // 包的父类
//    public static String table = "ae_topic"; // 要生成的表
//
//
//    public static void main(String[] args) {
//        //需要构建一个代码自动生成器对象
//        AutoGenerator mpg = new AutoGenerator();
//        //配置策略
//
//        //1、全局配置
//        GlobalConfig gc = new GlobalConfig();
//
//        String projectPath = System.getProperty("user.dir");
//
//        gc.setOutputDir(projectPath+"/src/main/java");//设置输出路径
//        gc.setAuthor("Nuisance");//设置作者
//        gc.setOpen(false);//是否打开资源管理器
//        gc.setFileOverride(true);//是否覆盖
////        gc.setFileOverride(false);//是否覆盖
//        gc.setServiceName("%sService");//去 Service 的I前缀
//        gc.setDateType(DateType.ONLY_DATE);//日期格式
//        mpg.setGlobalConfig(gc);
//
//        //设置数据源
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/"+Schemas+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&characterEncoding=utf8");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername(Root);
//        dsc.setPassword(Pwd);
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//
//        //3、包的配置
//        PackageConfig pc = new PackageConfig();
////        pc.setModuleName(Blog);
//        pc.setParent(parent);
//        pc.setEntity("entity");//实体类名
//        pc.setMapper("mapper");
//        pc.setService("service");
//        pc.setController("controller");
//        mpg.setPackageInfo(pc);
//
//        //策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude(table);//设置生成的表名，可以设置多个，不设置则为全部表 ----------------------------------
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
////      strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
//        strategy.setEntityLombokModel(true); //使用lombok
//        // strategy.setRestControllerStyle(true);
////      strategy.setLogicDeleteFieldName("deleted"); //逻辑删除字段
//        //自动填充
////        TableFill createTime = new TableFill("createTime", FieldFill.INSERT);
////      TableFill gmt_modified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
////
////        ArrayList<TableFill> tableFills = new ArrayList<>();
////        tableFills.add(createTime);
////      tableFills.add(gmt_modified);
////        strategy.setTableFillList(tableFills);
//        // strategy.setVersionFieldName("version");//乐观锁
//        strategy.setControllerMappingHyphenStyle(true); //localhost:8080/hello_id_2  //下划线命名
//
//        mpg.setStrategy(strategy);
//
//        mpg.execute();//执行
//    }
//}
