package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAllByNameContainsIgnoreCase(String name);
}
