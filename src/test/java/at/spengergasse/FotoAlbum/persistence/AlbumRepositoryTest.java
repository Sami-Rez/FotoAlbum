package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.TestContainerConfiguration;
import at.spengergasse.FotoAlbum.domain.Album;
import at.spengergasse.FotoAlbum.domain.Photo;
import at.spengergasse.FotoAlbum.domain.Photographer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static at.spengergasse.FotoAlbum.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)
class AlbumRepositoryTest {

    private @Autowired AlbumRepository repository;

    @Test
    void ensureSaveAndReReadWorks() {
        // given
        Photographer ac = acPhotographer();
        Photo p1 = photo("My 1st Photo");
        Photo p2 = photo("My 2nd Photo", 640, 480);
        Album album = album("My Album").addPhotos(p1, p2);

        // when
        var saved = repository.saveAndFlush(album);

        // then
        assertThat(saved).isSameAs(album);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getOwner().getId()).isNotNull();
        assertThat(p1.getId()).isNotNull();
        assertThat(p2.getId()).isNotNull();
    }

    @Test
    void ensureFindAllByNameContainsIgnoreCaseWorks() {
        // given
        Photographer ac = acPhotographer();
        Photo p1 = photo("My 1st Photo");
        Photo p2 = photo("My 2nd Photo", 640, 480);
        Album album = album("My Album").addPhotos(p1, p2);
        Photo p3 = photo("My 3rd Photo");
        Photo p4 = photo("My 4th Photo", 640, 480);
        Album album2 = album("Family Album").addPhotos(p3, p4);
        repository.saveAll(List.of(album, album2));

        // when
        List<Album> found = repository.findAllByNameContainsIgnoreCase("family");

        // then
        assertThat(found).containsExactly(album2);
    }
}