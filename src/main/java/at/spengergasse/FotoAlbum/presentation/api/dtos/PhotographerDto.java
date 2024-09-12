package at.spengergasse.FotoAlbum.presentation.api.dtos;

import at.spengergasse.FotoAlbum.domain.Photographer;

public record PhotographerDto(String userName, String firstName, String lastName) {
    public PhotographerDto(Photographer owner) {
        this(owner.getUsername().value(), owner.getFirstName(), owner.getLastName());
    }
}
