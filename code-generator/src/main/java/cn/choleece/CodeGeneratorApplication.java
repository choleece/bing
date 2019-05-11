package cn.choleece;

import cn.choleece.code.util.PropertiesFileUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 代码生成器
 * @author sf
 */
public class CodeGeneratorApplication {

	/**
	 * 全局配置
	 * @param mpg
	 */
	private static void globalConfig(AutoGenerator mpg, String projectPath) {
		GlobalConfig gc = new GlobalConfig();

		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor(PropertiesFileUtil.getInstance().get("author"));
		gc.setOpen(false);
		mpg.setGlobalConfig(gc);
	}

	/**
	 * 数据源配置
	 * @param mpg
	 */
	private static void dataSourceConfig(AutoGenerator mpg) {
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl(PropertiesFileUtil.getInstance().get("db.url"));
		dsc.setDriverName(PropertiesFileUtil.getInstance().get("db.driver"));
		dsc.setUsername(PropertiesFileUtil.getInstance().get("db.user"));
		dsc.setPassword(PropertiesFileUtil.getInstance().get("db.password"));
		mpg.setDataSource(dsc);
	}

	/**
	 * 包配置
	 * @param mpg
	 */
	private static void packageConfig(AutoGenerator mpg, PackageConfig pc) {

		pc.setModuleName(PropertiesFileUtil.getInstance().get("module.name"));
		pc.setParent(PropertiesFileUtil.getInstance().get("parent.package.name"));
		mpg.setPackageInfo(pc);
	}

	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();
		String projectPath = System.getProperty("user.dir");

		globalConfig(mpg, projectPath);
		dataSourceConfig(mpg);
		PackageConfig pc = new PackageConfig();
		packageConfig(mpg, pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		String templatePath = "/templates/mapper.xml.vm";
		// 设置模版引擎
		mpg.setTemplateEngine(new VelocityTemplateEngine());

		// 自定义输出配置
		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名
				return projectPath + PropertiesFileUtil.getInstance().get("mapper.xml.path") + pc.getModuleName()
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
		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setSuperEntityClass(PropertiesFileUtil.getInstance().get("super.entity.class"));
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setSuperControllerClass(PropertiesFileUtil.getInstance().get("super.controller.class"));
		strategy.setInclude(tables.split(","));
		strategy.setSuperEntityColumns("id");
		strategy.setControllerMappingHyphenStyle(true);
		strategy.setTablePrefix(pc.getModuleName() + "_");
		mpg.setStrategy(strategy);
		mpg.execute();
	}
}

