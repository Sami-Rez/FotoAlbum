package at.spengergasse.FotoAlbum.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@NoArgsConstructor

@Entity
@Table(name = "persons")
public class Person extends User {
    public static final int LENGTH_NICKNAME = 16;
    public static final int LENGTH_KEY = 40;

    @Column(length = LENGTH_NICKNAME, nullable = false)
    private @NotBlank String nickName;

    @Column(name = "person_key", length = LENGTH_KEY, nullable = false)
    private @NotBlank String key;

    @Builder
    public Person(EmailAddress username, String password, String firstName, String lastName, String nickName, String key) {
        super(username, password, firstName, lastName);
        this.nickName = nickName;
        this.key = key;
    }

    public static final Comparator<Person> byFirstName = Comparator.comparing(Person::getFirstName);
    public static final Comparator<Person> byLastName = Comparator.comparing(Person::getLastName);
    public static final Comparator<Person> byLastNameDesc = byLastName.reversed();
    public static final Comparator<Person> byNickName = Comparator.comparing(Person::getNickName);
    public static final Comparator<Person> byName = byLastName.thenComparing(byFirstName).thenComparing(byNickName);
}
