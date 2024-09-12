package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.domain.EmailAddress;
import at.spengergasse.FotoAlbum.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByUsername(EmailAddress username);

    Optional<Person> findByKey(String key);

    List<Person> findAllByLastNameLikeIgnoreCase(String lastNamePart);

    void deleteByKey(String key);
}
