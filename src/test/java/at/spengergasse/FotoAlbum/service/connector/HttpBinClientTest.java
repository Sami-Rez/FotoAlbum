package at.spengergasse.FotoAlbum.service.connector;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HttpBinClientTest {

    private @Autowired HttpBinClient httpBinClient;

    @Test
    void ensureRetrieveKeyReturnsAValue() {
        // expect
        assertThat(httpBinClient.retrieveKey()).isNotNull();
    }
}