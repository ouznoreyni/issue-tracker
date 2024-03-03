package sn.noreyni.issuetrackerbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${application.urls.dev-url}")
    private String DEV_URL;

    @Value("${application.urls.prod-url}")
    private String PROD_URL;
    @Bean
    public OpenAPI issueTrackerOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(DEV_URL);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(PROD_URL);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setName("Noreyni Dev");
        contact.setUrl("https://www.noreyni.sn/");

        Info info = new Info()
                .title("Issue Tracker ")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Issue tracker Appp. Our issue tracker project is a dynamic and user-centric platform designed to streamline project management and issue resolution. Users can enrich issue descriptions by attaching relevant files and images, fostering comprehensive communication. ");

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
