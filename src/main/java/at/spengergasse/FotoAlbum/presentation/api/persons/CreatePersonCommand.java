package at.spengergasse.FotoAlbum.presentation.api.persons;

import jakarta.validation.constraints.NotBlank;

public record CreatePersonCommand(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String nickname) {
}
