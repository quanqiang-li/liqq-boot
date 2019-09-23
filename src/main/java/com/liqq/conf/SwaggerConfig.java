package com.liqq.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置,限定生效的环境是dev和test
 * 
 * @author carl
 *
 */
@Profile(value = {"dev","test"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.liqq.controller")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	/**
	 * 文档说明
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		String title = "api文档";
		String description = "整合swagger提供api接口文档";
		String version = "V1.0";
		String termsOfServiceUrl = "http://example.com";
		String contactName = "liqq";
		String license = "os2.0";
		String licenseUrl = "http://licence.com";
		return new ApiInfo(title, description, version, termsOfServiceUrl, contactName, license, licenseUrl);
		
	}
}
