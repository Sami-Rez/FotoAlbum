package at.spengergasse.FotoAlbum.service;


import at.spengergasse.FotoAlbum.foundation.DateTimeFactory;
import at.spengergasse.FotoAlbum.persistence.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    private TokenService tokenService;
    private final LocalDateTime now = LocalDateTime.now();

    private @Mock TokenRepository tokenRepository;
    private DateTimeFactory dateTimeFactory;

    @BeforeEach
    void setup() {
        assumeThat(tokenRepository).isNotNull();
        dateTimeFactory = mock(DateTimeFactory.class);
        when(dateTimeFactory.now()).thenReturn(now);
        tokenService = new TokenService(tokenRepository, dateTimeFactory);
    }

  /*  @Test
    void ensureCreateToken() {
         ...
    }*/

}