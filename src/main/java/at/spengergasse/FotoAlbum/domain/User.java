package at.spengergasse.FotoAlbum.domain;


import at.spengergasse.FotoAlbum.foundation.StrongPassword;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
public abstract class User extends AbstractPersistable<Long> {

    public static final int LENGTH_USERNAME = 64;
    public static final int LENGTH_PASSWORD = 128;
    public static final int LENGTH_FIRSTNAME = 64;
    public static final int LENGTH_LASTNAME = 32;

    @Column(length = LENGTH_USERNAME)
    private @NotNull EmailAddress username;
    @Column(length = LENGTH_PASSWORD, nullable = false)
    @StrongPassword
    private @NotBlank String password;
    @Column(length = LENGTH_FIRSTNAME, nullable = false)
    private @NotBlank String firstName;
    @Column(length = LENGTH_LASTNAME, nullable = false)
    private @NotBlank String lastName;
}
