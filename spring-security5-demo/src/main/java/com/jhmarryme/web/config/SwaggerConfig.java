package com.jhmarryme.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 * @author JiaHao Wang
 * @date 2020/9/11 9:41
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enableSwagger;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 是否启用, 通过配置文件配置
                .enable(enableSwagger)
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.imooc.web.controller"))
                // 另一种配置, 扫描使用了@API注解的类
                // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("security API，使用 Swagger2 构建RESTFul API")
                //创建人
                .contact(new Contact("com.jhmarryme", "https://jhmarryme.cn/", "com.jhmarryme@gmail.com"))
                //版本号
                .version("1.0")
                //描述
                .description("security API，创建于2020年")
                .build();
    }
}
