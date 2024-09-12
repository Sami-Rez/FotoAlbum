package at.spengergasse.FotoAlbum.service;




import at.spengergasse.FotoAlbum.domain.Album;
import at.spengergasse.FotoAlbum.persistence.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static at.spengergasse.FotoAlbum.TestFixtures.album;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    private AlbumService albumService;

    private @Mock AlbumRepository albumRepository;

    @BeforeEach
    void setup() {
        assumeThat(albumRepository).isNotNull();
        albumService = new AlbumService(albumRepository);
    }

    @Test
    void ensureFetchAlbumsWithNoArgumentCallFindAll() {
        // given
        Optional<String> searchCriteria = Optional.empty();
        Album album = album("Test Album");
        when(albumRepository.findAll()).thenReturn(List.of(album));

        // when
        var result = albumService.fetchAlbums(searchCriteria);

        // then
        assertThat(result).containsExactly(album);
        // gleich wie unten: verify(albumRepository, times(1)).findAll();
        verify(albumRepository).findAll();
        verifyNoMoreInteractions(albumRepository);
    }

    @Test
    void ensureFetchAlbumsWithValidArgumentCallFindAllByNameContainsIgnoreCase() {
        // given
        String searchString = "Test";
        Optional<String> searchCriteria = Optional.of(searchString);
        var album = album("Test Album");
        when(albumRepository.findAllByNameContainsIgnoreCase(searchString)).thenReturn(List.of(album));

        // when
        var result = albumService.fetchAlbums(searchCriteria);

        // then
        assertThat(result).containsExactly(album);
        // gleich wie unten: verify(albumRepository, times(1)).findAll();
        verify(albumRepository).findAllByNameContainsIgnoreCase(any());
        verifyNoMoreInteractions(albumRepository);
    }
}