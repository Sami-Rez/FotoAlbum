package at.spengergasse.FotoAlbum.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tokens")
public class Token extends AbstractPersistable<Long> {

    @Column(name = "token_value", nullable = false)
    private @NotBlank String value;

    @Column(name = "token_type")
    private @NotNull TokenType type;

    private String targetClass;

    private LocalDateTime expirationTS;
}
