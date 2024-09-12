package at.spengergasse.FotoAlbum.persistence;


import at.spengergasse.FotoAlbum.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    boolean existsByValue(String value);

    void deleteByExpirationTSBefore(LocalDateTime cutoff);
}
