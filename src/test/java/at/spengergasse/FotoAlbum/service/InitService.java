package at.spengergasse.FotoAlbum.service;


import at.spengergasse.FotoAlbum.domain.Album;
import at.spengergasse.FotoAlbum.domain.Photo;
import at.spengergasse.FotoAlbum.domain.Photographer;
import at.spengergasse.FotoAlbum.persistence.AlbumRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static at.spengergasse.FotoAlbum.TestFixtures.*;

@RequiredArgsConstructor

@Service
public class InitService {

    private final AlbumRepository albumRepository;

    @PostConstruct
    public void init() {
        Photographer ac = acPhotographer();
        Photo p1 = photo("My 1st Photo");
        Photo p2 = photo("My 2nd Photo", 640, 480);
        Album album = album("My Album").addPhotos(p1, p2);

        albumRepository.save(album);
    }
}
