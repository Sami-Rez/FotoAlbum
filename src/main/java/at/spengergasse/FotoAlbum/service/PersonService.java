package at.spengergasse.FotoAlbum.service;


import at.spengergasse.FotoAlbum.domain.EmailAddress;
import at.spengergasse.FotoAlbum.domain.Person;
import at.spengergasse.FotoAlbum.domain.Token;
import at.spengergasse.FotoAlbum.persistence.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;
    private final TokenService tokenService;

    public Person register(String username, String password, String firstName, String lastName, String nickname) {
        log.debug("Check if person '{}' exists in DB", username);
        var exists = personRepository.existsByUsername(new EmailAddress(username));

        if (exists) {
            log.warn("Person '{}' already exists -> throw Exception", username);
            throw UserAlreadyExistsException.forExistingUsername(username); // redirect macht der Controller
        }

        Person person = Person.builder()
                              .username(new EmailAddress(username))
                              .password(password)
                              .firstName(firstName)
                              .lastName(lastName)
                              .nickName(nickname)
                              .key(tokenService.createApiKey(Person.class).getValue())
                              .build();
        personRepository.save(person);
        log.info("Person '{} (id={}, key={})' successfully saved", username, person.getId(), person.getKey());

        Token token = tokenService.createRegistrationToken();
        log.info("Created confirmation token '{}' and linked it to person '{} (id={})'", token, username, person.getId());

        // todo send confirmation email
        log.debug("Send registration confirmation email for user '{}'", username);
        return person;
    }

    @Transactional(readOnly = true)
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Person> searchPersons(String lastNamePart) {
        return personRepository.findAllByLastNameLikeIgnoreCase(lastNamePart);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPerson(Long id) {
        return personRepository.findById(id);
    }

    public void deletePersonByKey(String key) {
        personRepository.deleteByKey(key);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPersonByKey(String key) {
        return personRepository.findByKey(key);
    }

    public Person updatePerson(String key, String firstName, String lastName, String nickname) {
        return personRepository.findByKey(key)
                               .map(person -> {
                                   person.setFirstName(firstName);
                                   person.setLastName(lastName);
                                   person.setNickName(nickname);
                                   return person;
                               })
//                               .map(personRepository::save)
                               .orElseThrow(() -> PersonDoesNotExistException.forKey(key));
    }
}
