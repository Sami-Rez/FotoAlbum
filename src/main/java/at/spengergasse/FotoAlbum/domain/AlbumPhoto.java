package at.spengergasse.FotoAlbum.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
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
public class AlbumPhoto {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_album_photos_2_photos"))
    private @NotNull @Valid Photo photo;
}
