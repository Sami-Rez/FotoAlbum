package at.spengergasse.FotoAlbum.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Embeddable
public class Location {
    private @NotNull @Min(-180) @Max(180) Double longitude;
    private @NotNull @Min(-90) @Max(90) Double latitude;
}