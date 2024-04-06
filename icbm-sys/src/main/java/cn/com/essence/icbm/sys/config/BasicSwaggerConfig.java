package cn.com.essence.icbm.sys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Configuration
public class BasicSwaggerConfig {

    @Value("${springfox.documentation.swagger-ui.is.enable:true}")
    private Boolean swaggerEnable;

    // 定义分隔符
    private static final String SPLITTER = ";";

    @Bean("_icbmSwaggerConfig")
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("ICBM-APP-Basic")
                .enable(swaggerEnable)
                .select()
                // 方法需要有ApiOperation注解才能生存接口文档
                .apis(basePackage("cn.com.essence.icbm.sys" + SPLITTER + "cn.com.essence.wefa"))
                // 路径使用any风格
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContexts()))
                .securitySchemes(Collections.singletonList(securitySchemes()))
                // 接口文档的基本信息
                .apiInfo(apiInfo());
    }

    /**
     * 接口文档详细信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("投资业务系统- APP(基础应用)").description("API文档").version("1.0.0").build();
    }

    private SecurityScheme securitySchemes() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("Authorization", "登陆凭证");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(SPLITTER)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

}
