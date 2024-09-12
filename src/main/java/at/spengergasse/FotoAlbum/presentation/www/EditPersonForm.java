package at.spengergasse.FotoAlbum.presentation.www;


import at.spengergasse.FotoAlbum.domain.Person;
import at.spengergasse.FotoAlbum.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditPersonForm(
        @Email @NotBlank String username,
        @NotBlank @Size(min = 2, max = User.LENGTH_FIRSTNAME) String firstName,
        @NotBlank @Size(min = 2, max = User.LENGTH_LASTNAME) String lastName,
        @NotBlank String nickname
) {
    public static EditPersonForm create(Person person) {
        return new EditPersonForm(person.getUsername().value(), person.getFirstName(), person.getLastName(), person.getNickName());
    }
}

