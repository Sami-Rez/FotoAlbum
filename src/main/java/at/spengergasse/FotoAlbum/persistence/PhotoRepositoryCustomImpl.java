package at.spengergasse.FotoAlbum.persistence;



import at.spengergasse.FotoAlbum.domain.*;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
public class PhotoRepositoryCustomImpl extends QuerydslRepositorySupport implements PhotoRepositoryCustom {

    private QPhoto photo = QPhoto.photo;

    public PhotoRepositoryCustomImpl() {
        super(Photo.class);
    }

//    private final EntityManager em;
//    private final JdbcTemplate jdbcTemplate;
//    private final JdbcClient jdbcClient;

    @Override
    public List<Photo> complexQuery(Photographer photographer, LocalDateTime start, LocalDateTime end) {
        return from(photo)
                .where(photo.photographer.eq(photographer),
                        photo.creationTimeStamp.between(start, end))
                .fetch();
    }

    public List<PhotoProjections.Overview> overview(Photographer photographer, LocalDateTime start, LocalDateTime end) {
        return from(photo)
                .where(photo.photographer.eq(photographer),
                        photo.creationTimeStamp.between(start, end))
                .select(new QPhotoProjections_Overview(photo.name, photo.orientation))
                .fetch();
    }
}
