package pl.mateusz.redosz.task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient restClient(@Value("${github.url}") String githubUrl) {
        return RestClient.builder()
                .baseUrl(githubUrl)
                .build();
    }
}
