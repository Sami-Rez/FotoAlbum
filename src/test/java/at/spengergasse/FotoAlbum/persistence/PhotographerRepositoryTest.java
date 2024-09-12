package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.TestContainerConfiguration;
import at.spengergasse.FotoAlbum.domain.EmailAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static at.spengergasse.FotoAlbum.TestFixtures.acPhotographer;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)
class PhotographerRepositoryTest {

    private @Autowired PhotographerRepository repository;

    @Test
    void ensureSaveAndReReadWorks() {
        // given
        var photographer = acPhotographer().addEmails(new EmailAddress("c@spg.at"),
                new EmailAddress("chwatal@spengergasse.at"));

        //when
        var saved = repository.saveAndFlush(photographer);

        // then
        assertThat(saved).isSameAs(photographer);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStudioAddress().getCountry().getId()).isNotNull();
    }
}