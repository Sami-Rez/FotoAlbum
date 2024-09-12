package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.domain.Photo;
import at.spengergasse.FotoAlbum.domain.PhotoProjections;
import at.spengergasse.FotoAlbum.domain.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>, PhotoRepositoryCustom {

    List<Photo> findAllByPhotographerAndCreationTimeStampBetween(Photographer photographer, LocalDateTime startTS, LocalDateTime endTS);

    List<PhotoProjections.Overview> findAllByNameLikeIgnoreCase(String name);

    Long countByPhotographer(Photographer photographer);
}
