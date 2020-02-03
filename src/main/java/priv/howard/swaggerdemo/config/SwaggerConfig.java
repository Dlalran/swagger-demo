package priv.howard.swaggerdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    注入Swagger实例Docket,类型为Swagger2
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                是否启动Swagger，可以配合当前profile判断实现动态开启和关闭
                .enable(true)
//                通过groupName可以进行API分组
                .groupName("group1")
//                通过select方法返回一个Docker的builder，通过其方法来指定Docket参数，最终build出一个docker实例
                .select()
//                RequestHandlerSelectors配置扫描接口的范围，basePackage指定要扫描的包名，any扫描全部，none不扫描
//                还可以根据指定的注解withClassAnnotation、withMethodAnnotation进行扫描
                .apis(RequestHandlerSelectors.basePackage("priv.howard.swaggerdemo.controller"))
//                PathSelectors可以通过Ant方式或者Regex正则表达式来对路径进行筛选
                .paths(PathSelectors.ant("/**"))
                .build();
    }

//    API文档信息类
    private ApiInfo apiInfo() {
        Contact contact = new Contact("Howard", "https://www.baidu.com", "test@qq.com");
//        参数分别为API文档标题、文档描述、版本、服务条款URL、联系信息对象、许可证信息、许可证URL、拓展信息
        return new ApiInfo("Swagger Demo",
                "A Swagger Demo Application API Documentation",
                "1.0",
                "https://www.baidu.com",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
