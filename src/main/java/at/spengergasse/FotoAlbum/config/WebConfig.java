package at.spengergasse.FotoAlbum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class WebConfig {

    @Bean
    public RestClient httpBin(RestClient.Builder builder, @Value("${httpbin.uri}") String httpbinUri) {
        return builder.baseUrl(httpbinUri)
                      .requestFactory(new JdkClientHttpRequestFactory())
                      .build();
    }
}
