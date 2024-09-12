package at.spengergasse.FotoAlbum.domain;

import lombok.Getter;

@Getter
public enum TokenType {
    API_KEY(8),
    REGISTRATION(6, 60),
    RESET_PASSWORD(6, 15);

    private final Integer length;
    private final Integer expiresInSeconds;

    TokenType(Integer length) {
        this(length,null);
    }

    TokenType(Integer length, Integer expiresInSeconds) {
        this.length = length;
        this.expiresInSeconds = expiresInSeconds;
    }
}
