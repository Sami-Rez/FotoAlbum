package at.spengergasse.FotoAlbum.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "countries")
public class Country extends AbstractPersistable<Long> {
    public static final int LENGTH_NAME = 64;
    public static final int LENGTH_ISO_2_CODE = 2;
    public static final int LENGTH_TOP_LEVEL_DOMAIN = 4;

    @Column(name = "country_name", length = LENGTH_NAME, nullable = false)
    private @NotBlank String name;

    @Column(length = LENGTH_ISO_2_CODE, nullable = false)
    private @NotBlank String iso2Code;

    @Column(length = LENGTH_TOP_LEVEL_DOMAIN, nullable = false)
    private @NotBlank String topLevelDomain;

    private @Min(1) @Max(9999) Integer countryCode;
}
