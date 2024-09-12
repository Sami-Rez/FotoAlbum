package at.spengergasse.FotoAlbum.presentation.api.persons;

import at.spengergasse.FotoAlbum.domain.Person;

public record PersonDto(String username, String firstName, String lastName, String nickName) {
    public PersonDto(Person person) {
        this(person.getUsername().value(), person.getFirstName(), person.getLastName(), person.getNickName());
    }
}
