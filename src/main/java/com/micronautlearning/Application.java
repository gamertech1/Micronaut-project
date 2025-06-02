package com.micronautlearning;

import com.micronautlearning.helloapp.HelloController;
import io.micronaut.openapi.annotation.OpenAPIGroupInfo;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(
        info = @Info(
                title = "Micronaut project",
                version = "${my.api.version}",
                description = "{my.api.description}",
                license = @License(name = "MIT")

        )
)
public class Application {
    public static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        var context = Micronaut.run(Application.class, args);
        LOG.info("Message Service: {}", context.getBean(HelloController.class).helloworld());
    }
}