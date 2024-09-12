package at.spengergasse.FotoAlbum.service.connector;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j

@Component
public class HttpBinClient {

    private final RestClient httpBin;

    public String retrieveKey() {
        log.debug("Retrieve unique key!");
        Map<String, String> keyResponse = httpBin.get()
                                                 .uri("/uuid")
                                                 .retrieve()
                                                 .toEntity(new ParameterizedTypeReference<Map<String, String>>() {
                                                 })
                                                 .getBody();
        String key = keyResponse.get("uuid");
        log.info("Key: {}", key);
        return key;
    }
}
