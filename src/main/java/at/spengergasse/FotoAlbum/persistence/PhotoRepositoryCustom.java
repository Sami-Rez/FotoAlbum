package at.spengergasse.FotoAlbum.persistence;

import at.spengergasse.FotoAlbum.domain.Photo;
import at.spengergasse.FotoAlbum.domain.PhotoProjections;
import at.spengergasse.FotoAlbum.domain.Photographer;



import java.time.LocalDateTime;
import java.util.List;


public interface PhotoRepositoryCustom {

    List<Photo> complexQuery(Photographer photographer, LocalDateTime start, LocalDateTime end);

    List<PhotoProjections.Overview> overview(Photographer photographer, LocalDateTime start, LocalDateTime end);
}
