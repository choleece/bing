package cn.choleece;

import cn.choleece.code.util.PropertiesFileUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeGeneratorApplication {

	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		String projectPath = System.getProperty("user.dir");
		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor("jobob");
		gc.setOpen(false);
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl(PropertiesFileUtil.getInstance().get("db.url"));
		dsc.setDriverName(PropertiesFileUtil.getInstance().get("db.driver"));
		dsc.setUsername(PropertiesFileUtil.getInstance().get("db.user"));
		dsc.setPassword(PropertiesFileUtil.getInstance().get("db.password"));
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName(PropertiesFileUtil.getInstance().get("module.name"));
		pc.setParent("com.baomidou.ant");
		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		String templatePath = "/templates/mapper.xml.vm";

		// 自定义输出配置
		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名
				return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
						+ "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
			}
		});

		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig();

		// 配置自定义输出模板
		// templateConfig.setEntity();
		// templateConfig.setService();
		// templateConfig.setController();

		templateConfig.setXml(null);
		mpg.setTemplate(templateConfig);

		// 获取要生成的表，用","隔开
		String tables = PropertiesFileUtil.getInstance().get("tables");
		Arrays.asList((tables.split(","))).forEach(table -> {
			// 策略配置
			StrategyConfig strategy = new StrategyConfig();
			strategy.setNaming(NamingStrategy.underline_to_camel);
			strategy.setColumnNaming(NamingStrategy.underline_to_camel);
			strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
			strategy.setEntityLombokModel(true);
			strategy.setRestControllerStyle(true);
			strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
			strategy.setInclude(table);
			strategy.setSuperEntityColumns("id");
			strategy.setControllerMappingHyphenStyle(true);
			strategy.setTablePrefix(pc.getModuleName() + "_");
			mpg.setStrategy(strategy);
			mpg.setTemplateEngine(new FreemarkerTemplateEngine());
			mpg.execute();
		});
	}
}

