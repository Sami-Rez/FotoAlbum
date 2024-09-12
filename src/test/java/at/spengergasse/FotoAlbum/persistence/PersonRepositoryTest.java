package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.TestContainerConfiguration;
import at.spengergasse.FotoAlbum.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static at.spengergasse.FotoAlbum.TestFixtures.uk;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void ensureSaveAndReReadWorks() {
        // given
        Person person = uk();
        // when
        var saved = repository.saveAndFlush(person);

        // then
        assertThat(saved).isSameAs(person);
        assertThat(saved.getId()).isNotNull();
    }
}