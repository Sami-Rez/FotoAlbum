package at.spengergasse.FotoAlbum.presentation.api;

import at.spengergasse.FotoAlbum.domain.Person;
import at.spengergasse.FotoAlbum.presentation.api.persons.CreatePersonCommand;

public class RestFixtures {

    public static CreatePersonCommand createPersonCmd(Person person) {
        return new CreatePersonCommand(person.getUsername().value(), person.getPassword(), person.getFirstName(), person.getLastName(), person.getNickName());
    }
}
