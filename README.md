# Swagger

​		Swagger是一款RESTful接口的文档自动生成和功能测试的工具，可以帮助对前后端分离的API接口进行设计、编写文档、测试和部署。

---

## 添加依赖

​		Swagger的依赖包含核心功能和UI两部分

```xml
<!--        Swagger-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${swagger.version}</version>
        </dependency>
<!--        Swagger UI-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${swagger.version}</version>
        </dependency>
```

---

## 配置

​		可以在Spring Boot主配置类中加入`@EnableSwagger2`来直接进行使用，如果需要自定义信息则创建一个单独的配置类，Swagger的实例是一个Docket对象，该对象的属性进行设置并注入到IOC容器从而实现对Swagger的自定义设置，例如`apiInfo`对应的文档描述信息、`enable`实现的功能动态开闭、`select`通过Builder构造器模式对扫描的接口、路径进行筛选，并配合`groupName`的分组和注入多个Docket可以对接口进行分组。

```java
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
//                RequestHandlerSelectors配置扫描接口的范围，basePackage指定扫描的包名，any扫描全部，none不扫描
//                还可以根据指定的类、方法注解withClassAnnotation、withMethodAnnotation进行扫描
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
```

​		Swagger会默认扫描所有的如Controller等API接口或者扫描配置中指定的路径或包内API，并通过Mapping注解获取其对应的Http请求方法，对于实体类(Model)则仅扫描在API中被调用的(如作为参数传入或者作为返回值)。

---

## 注解

- `@Api`是作用在类上的，如`tag`指定其在文档中的名字，`description`指定其描述信息等

- `@ApiOperation`是作用在接口的方法上的，如`value`指定其名字，`notes`指定其描述信息，`response`指定返回的类型，`code`请求正常返回的状态码等

- `@ApiResponses`作用在接口方法上，可以通过`@ApiResponse`对每个返回的状态码进行枚举，如`code`指定每个状态码，`message`指定状态码描述信息

- `@ApiParam`作用在方法的参数上，如`value`描述属性、`name`重写属性名、`example`指定非POST方式传参实例，`required`指定是否必填、`defaultValue`指定默认值等

- `@ApiImplicitParams`作用在方法上，内部通过注解`@ApiImplicitParam`描述每个方法参数，代替在参数前添加注解。

  参数和作用与上一注释类似，有一额外属性`paramType`指定获取参数的方式，`header`为获取请求头参数(相当于`@RequestHeader`)、`query`为解析请求参数(等于`@RequestParam`)、`path`为从url中获取参数(等于`@PathVariable`)、`body`为获取请求体中的参数并封装为json等

- `@ApiModel`作用在实体类上，`value`重写类名，`description`给出描述信息

- `@ApiModelProperty`作用在实体类属性上，`value`对字段进行描述，`name`重写属性名，`example`给出属性示例，`required`指定是否必填

---

## 文档

​		运行后访问http://服务地址/swagger-ui.html以访问Swagger接口文档；左上是通过`ApiInfo`指定的文档以及作者信息，右边可以通过分组进行查看；中间是每个接口的描述信息，其中包含接口参数以及返回的Response和状态码，可以点击Try it out并输入参数对接口进行测试；接口信息下面是实体类信息。

