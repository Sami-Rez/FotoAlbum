package at.spengergasse.FotoAlbum.presentation.api.persons;

import at.spengergasse.FotoAlbum.TestFixtures;
import at.spengergasse.FotoAlbum.service.PersonService;
import at.spengergasse.FotoAlbum.service.UserAlreadyExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static at.spengergasse.FotoAlbum.presentation.api.RestFixtures.createPersonCmd;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonRestController.class)
class PersonRestControllerTest {

    private @Autowired MockMvc mockMvc;
    private @Autowired ObjectMapper mapper;
    private @MockBean PersonService personService;

    @BeforeEach
    void setup() {
        assumeThat(mockMvc).isNotNull();
        assumeThat(personService).isNotNull();
    }


    @Test
    void ensureGetPersonReturnsNoFoundForNonExistingId() throws Exception {
        // given
        long id = 4711l;
        when(personService.getPerson(eq(id))).thenReturn(Optional.empty());

        // expect
        var request = get(PersonRestController.ROUTE_ID, id).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
               .andExpect(status().isNotFound())
               .andDo(print());
    }

    @Test
    void ensureGetPersonsReturnsNoContentForMissingData() throws Exception {
        // given
        when(personService.getPersons()).thenReturn(Collections.emptyList());

        // expect
        var request = get(PersonRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNoContent()).andDo(print());
    }

    @Test
    void ensureGetPersonsReturnsOkForExistingData() throws Exception {
        // given
        var person = TestFixtures.person("Klaus", "Unger");
        when(personService.getPersons()).thenReturn(List.of(person));

        // expect
        var request = get(PersonRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].username").value("Klaus.Unger@spg.at"))
               .andDo(print());
    }

    @Test
    void ensureGetPersonsReturnsOkForExistingDataWithValidLastNamePart() throws Exception {
        // given
        String lastNamePart = "U";
        var person = TestFixtures.person("Klaus", "Unger");
        when(personService.searchPersons(lastNamePart)).thenReturn(List.of(person));

        // expect
        var request = get(PersonRestController.BASE_URL).param("lastNamePart", lastNamePart)
                                                        .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].username").value("Klaus.Unger@spg.at"))
               .andDo(print());
    }

    @Test
    void ensureGetPersonsReturnsNoContentForMissingDataWithValidLastNamePart() throws Exception {
        // given
        String lastNamePart = "X";
        var person = TestFixtures.person("Klaus", "Unger");
        when(personService.searchPersons(lastNamePart)).thenReturn(Collections.emptyList());

        // expect
        var request = get(PersonRestController.BASE_URL).param("lastNamePart", lastNamePart)
                                                        .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
               .andExpect(status().isNoContent())
               .andDo(print());
    }


    @Test
    void ensureRegisterPersonsReturnsOkForCorrectData() throws Exception {
        // given
        var person = spy(TestFixtures.person("Klaus", "Unger"));
        when(personService.register(any(), any(), any(), any(), any())).thenReturn(person);
        when(person.getId()).thenReturn(4711l);
        CreatePersonCommand cmd = createPersonCmd(person);

        // expect
        var request = post(PersonRestController.BASE_URL).accept(MediaType.APPLICATION_JSON)
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .content(mapper.writeValueAsString(cmd));

        mockMvc.perform(request)
               .andExpect(status().isCreated())
               .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/persons/4711"))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.username").value("Klaus.Unger@spg.at"))
               .andExpect(jsonPath("$.lastName").value("Unger"))
               .andExpect(jsonPath("$.firstName").value("Klaus"))
               .andExpect(jsonPath("$.nickName").value("KU"))
               .andDo(print());

    }

    @Test
    void ensureRegisterUserWithExistingUsernameReturnsProperProblemDetailInABadRequestResponse() throws Exception {
        // given
        var username = "unger@spengergasse.at";
        when(personService.register(eq(username), any(), any(), any(), any())).thenThrow(UserAlreadyExistsException.forExistingUsername(username));
        CreatePersonCommand cmd = new CreatePersonCommand(username, "pwd", "Klaus", "Unger", "UK");

        // expect
        var request = post(PersonRestController.BASE_URL).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(cmd));

        mockMvc.perform(request).andExpect(status().isConflict()).andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON)).andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value())).andExpect(jsonPath("$.title").value("UserRegistration")).andDo(print());
    }

    @Test
    void ensureRegisterUserProperProblemDetailInAInternalServerErrorResponseDueToPersistenceException() throws Exception {
        // given
        var username = "unger@spengergasse.at";
        when(personService.register(eq(username), any(), any(), any(), any())).thenThrow(new PersistenceException("Hätte ich bei Klaus Coufal bloss besser aufgepasst!"));
        CreatePersonCommand cmd = new CreatePersonCommand(username, "pwd", "Klaus", "Unger", "UK");

        // expect
        var request = post(PersonRestController.BASE_URL).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(cmd));

        mockMvc.perform(request).andExpect(status().isInternalServerError()).andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON)).andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value())).andExpect(jsonPath("$.title").value("Persistence problems")).andDo(print());
    }
}