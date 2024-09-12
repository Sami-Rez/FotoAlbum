package at.spengergasse.FotoAlbum.service;


import at.spengergasse.FotoAlbum.domain.Token;
import at.spengergasse.FotoAlbum.domain.TokenType;
import at.spengergasse.FotoAlbum.foundation.DateTimeFactory;
import at.spengergasse.FotoAlbum.persistence.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional
public class TokenService {

    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final SecureRandom RANDOM = new SecureRandom();
    private final TokenRepository tokenRepository;
    private final DateTimeFactory dateTimeFactory;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Token createApiKey(Class<?> clazz) {
        return createToken(TokenType.API_KEY, Optional.of(clazz));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Token createRegistrationToken() {
        return createToken(TokenType.REGISTRATION, Optional.empty());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Token createResetPasswordToken() {
        return createToken(TokenType.RESET_PASSWORD, Optional.empty());
    }

    private Token createToken(TokenType type, Optional<Class<?>> clazz) {
        String value;

        do {
            value = generateValue(type.getLength());
        } while (tokenRepository.existsByValue(value));

        LocalDateTime expirationTS = Optional.ofNullable(type.getExpiresInSeconds())
                                             .map(seconds -> dateTimeFactory.now().plusSeconds(seconds))
                                             .orElse(null);
        String targetClass = clazz.map(Class::getSimpleName).orElse(null);
        return tokenRepository.save(Token.builder().value(value)
                                         .expirationTS(expirationTS)
                                         .type(type)
                                         .targetClass(targetClass)
                                         .build());
    }

    @Async
    @Scheduled(fixedDelay = 60 * 1000)
    public void cleanupExpiredTokens() {
        tokenRepository.deleteByExpirationTSBefore(dateTimeFactory.now());
    }

    private String generateValue(int length) {
        char[] result = new char[length];

        for (int i = 0; i < length; ++i) {
            char pick = ALPHABET[RANDOM.nextInt(ALPHABET.length)];
            result[i] = pick;
        }

        return new String(result);
    }
}
