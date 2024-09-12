package at.spengergasse.FotoAlbum.service;

import lombok.Getter;

public class PersonDoesNotExistException extends RuntimeException {

    @Getter
    private final String key;

    private PersonDoesNotExistException(String key, String message) {
        super(message);
        this.key = key;
    }

    public static PersonDoesNotExistException forKey(String key) {
        String message = "Person with key %s does not exist!".formatted(key);
        return new PersonDoesNotExistException(key, message);
    }
}