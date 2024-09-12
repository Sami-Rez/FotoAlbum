package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.TestContainerConfiguration;
import at.spengergasse.FotoAlbum.domain.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static at.spengergasse.FotoAlbum.TestFixtures.photo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)
class PhotoRepositoryTest {

    private @Autowired PhotoRepository repository;

    @Test
    void ensureSaveAndReReadWorks() {
        // given / arrange
        Photo photo = photo("My Photo", 640, 480);

        assumeThat(repository).isNotNull();

        // when / act
        var saved = repository.saveAndFlush(photo);

        // then / assert
        assertThat(saved).isNotNull().isSameAs(photo);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getPhotographer().getId()).isNotNull();
        assertThat(saved.getPhotographer().getStudioAddress().getCountry().getId()).isNotNull();
    }

    @Test
    void ensureComplexQueryCanBeExecuted() {
        //given
        Photo photo = photo("My Photo", 640, 480);
        repository.saveAndFlush(photo);
        // expect
        assertThat(repository.complexQuery(photo.getPhotographer(), LocalDateTime.now(), LocalDateTime.now())).isNotNull();
    }

    @Test
    void ensureOverviewQueryCanBeExecuted() {
        //given
        Photo photo = photo("My Photo", 640, 480);
        repository.saveAndFlush(photo);
        // expect
        assertThat(repository.overview(photo.getPhotographer(), LocalDateTime.now(), LocalDateTime.now())).isNotNull();
    }
}