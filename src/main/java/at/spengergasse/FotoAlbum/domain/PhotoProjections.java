package at.spengergasse.FotoAlbum.domain;

import com.querydsl.core.annotations.QueryProjection;

public class PhotoProjections {
    public record Overview(String name, Orientation orientation) {
        @QueryProjection
        public Overview {
            // left blank intentionally - you could to some validations here
        }
    }
}
