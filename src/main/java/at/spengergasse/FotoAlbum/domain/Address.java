package at.spengergasse.FotoAlbum.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Embeddable
public class Address {
    public static final int LENGTH_STREET_NUMBER = 64;
    public static final int LENGTH_ZIP_CODE = 16;
    public static final int LENGTH_CITY = 64;

    @Column(length = LENGTH_STREET_NUMBER, nullable = false)
    private @NotBlank String streetNumber;
    @Column(length = LENGTH_ZIP_CODE, nullable = false)
    private @NotBlank String zipCode;
    @Column(length = LENGTH_CITY, nullable = false)
    private @NotBlank String city;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_address_2_countries"))
    private Country country;
}
