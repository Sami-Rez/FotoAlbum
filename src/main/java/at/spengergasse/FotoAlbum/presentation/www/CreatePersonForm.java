package at.spengergasse.FotoAlbum.presentation.www;


import at.spengergasse.FotoAlbum.domain.User;
import at.spengergasse.FotoAlbum.foundation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePersonForm(
        @Email @NotBlank String username,
        @StrongPassword String password,
        @NotBlank @Size(min = 2, max = User.LENGTH_FIRSTNAME) String firstName,
        @NotBlank @Size(min = 2, max = User.LENGTH_LASTNAME) String lastName,
        @NotBlank String nickname
) {
    public static CreatePersonForm create() {
        return new CreatePersonForm("", "", "", "", "");
    }
}

