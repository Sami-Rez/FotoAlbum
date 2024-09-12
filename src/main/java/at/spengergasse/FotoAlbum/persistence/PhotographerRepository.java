package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.domain.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
}
