package at.spengergasse.FotoAlbum.presentation.www;


import at.spengergasse.FotoAlbum.TestFixtures;
import at.spengergasse.FotoAlbum.domain.Person;
import at.spengergasse.FotoAlbum.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    private @Autowired MockMvc mockMvc;
    private @MockBean PersonService personService;

    @Test
    void ensureGetPersonsReturnsProperView() throws Exception {
        List<Person> persons = List.of(TestFixtures.uk(), TestFixtures.ac());
        when(personService.getPersons()).thenReturn(persons);

        // expect
        mockMvc.perform(get("/persons"))
               .andExpect(status().isOk())
               .andExpect(model().attribute("persons", persons))
               .andExpect(view().name("persons/list"))
               .andDo(print());
    }
}