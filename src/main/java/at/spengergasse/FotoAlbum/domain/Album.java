package at.spengergasse.FotoAlbum.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static at.spengergasse.FotoAlbum.foundation.Guard.isNotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "albums")
public class Album extends AbstractPersistable<Long> {
    public static final int LENGTH_NAME = 64;
    public static final int LENGTH_ALBUM_TYPE = 1;
    public static final int INITIAL_ALBUM_SIZE = 20;

    @Column(name = "album_name", length = LENGTH_NAME, nullable = false)
    private @NotBlank String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_albums_2_photographers"))
    private @NotNull @Valid Photographer owner;

    @Column(name = "album_type", length = LENGTH_ALBUM_TYPE, columnDefinition = "CHAR(1) DEFAULT 'D' CHECK album_type IN ('D', 'P') ", nullable = false)
    private AlbumType type;

    @Builder.Default
    @ElementCollection
    @JoinTable(name = "album_photos", foreignKey = @ForeignKey(name = "FK_album_photos_2_albums"))
    @OrderColumn(name = "position")
    private List<AlbumPhoto> albumPhotos = new ArrayList<>(INITIAL_ALBUM_SIZE);

    public void setAlbumPhotos(List<AlbumPhoto> albumPhotos) {
        this.albumPhotos = new ArrayList<>(INITIAL_ALBUM_SIZE);
        this.albumPhotos.addAll(albumPhotos);
    }

    public List<AlbumPhoto> getAlbumPhotos() {
        return Collections.unmodifiableList(albumPhotos);
    }

    public List<Photo> getPhotos() {
        return albumPhotos.stream()
                          .sorted()
                          .map(AlbumPhoto::getPhoto)
                          .collect(Collectors.toUnmodifiableList());
    }

    public Album addPhotos(Photo... photos) {
        Arrays.stream(photos).filter(isNotNull).forEach(p -> albumPhotos.add(AlbumPhoto.builder().photo(p).build()));
        return this;
    }

    public Album removePhotos(Photo... photos) {
        Arrays.stream(photos).filter(isNotNull).forEach(p -> {
            List<AlbumPhoto> foundAlbumPhotos = albumPhotos.stream().filter(ap -> ap.getPhoto().equals(p)).toList();
            albumPhotos.removeAll(foundAlbumPhotos);
        });

        return this;
    }

    public Album clearPhotos() {
        albumPhotos.clear();

        return this;
    }

    public Album insertPhotos(Integer position, Photo... photos) {
        AtomicInteger index = new AtomicInteger(position);
        Arrays.stream(photos).filter(isNotNull).forEach(p -> albumPhotos.add(index.getAndIncrement(), AlbumPhoto.builder().photo(p).build()));
        return this;
    }
}
