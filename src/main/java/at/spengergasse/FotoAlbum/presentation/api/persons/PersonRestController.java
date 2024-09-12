package at.spengergasse.FotoAlbum.presentation.api.persons;



import at.spengergasse.FotoAlbum.domain.Person;
import at.spengergasse.FotoAlbum.service.PersonService;
import at.spengergasse.FotoAlbum.service.UserAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Slf4j

@RestController
@RequestMapping(PersonRestController.BASE_URL)
public class PersonRestController {

    private static final String _SLASH = "/";
    private static final String PATHVAR_ID = "{id}";
    protected static final String BASE_URL = "/api/persons";
    protected static final String PATH_ID = _SLASH + PATHVAR_ID;
    protected static final String ROUTE_ID = BASE_URL + PATH_ID;

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDto>> getPersons(@RequestParam Optional<String> lastNamePart) {
        List<Person> persons = lastNamePart.map(personService::searchPersons)
                                           .orElseGet(personService::getPersons);
        return (persons.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(persons.stream().map(PersonDto::new).toList());
    }

    @GetMapping(PATH_ID)
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long id) {
        return personService.getPerson(id)
                            .map(PersonDto::new)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody @Valid CreatePersonCommand cmd) {
        Person person = personService.register(cmd.username(), cmd.password(), cmd.firstName(), cmd.lastName(), cmd.nickname());
        Link location = linkTo(methodOn(PersonRestController.class).getPerson(person.getId())).withSelfRel();
        return ResponseEntity.created(location.toUri()).body(new PersonDto(person));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistsException(UserAlreadyExistsException uaeEx) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, uaeEx.getMessage());
        problemDetail.setTitle("UserRegistration");
        problemDetail.setProperty("username", uaeEx.getUsername());
        return ResponseEntity.status(status).body(problemDetail);
    }
}
