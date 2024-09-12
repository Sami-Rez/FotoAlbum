package at.spengergasse.FotoAlbum.foundation;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeFactory {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
