package at.spengergasse.FotoAlbum.persistence.converter;


import at.spengergasse.FotoAlbum.domain.AlbumType;
import at.spengergasse.FotoAlbum.persistence.exception.DataQualityException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class AlbumTypeConverter implements AttributeConverter<AlbumType, String> {
    private static final String DIGITAL_KEY = "D";
    private static final String PHYSICAL_KEY = "P";

    @Override
    public String convertToDatabaseColumn(AlbumType email) {
        return Optional.ofNullable(email).map(e -> switch (e) {
            case DIGITAL -> DIGITAL_KEY;
            case PHYSICAL -> PHYSICAL_KEY;
        }).orElse(null);
    }

    @Override
    public AlbumType convertToEntityAttribute(String dbValue) {
        return Optional.ofNullable(dbValue).map(dbv -> switch (dbv) {
            case DIGITAL_KEY -> AlbumType.DIGITAL;
            case PHYSICAL_KEY -> AlbumType.PHYSICAL;
            default -> throw DataQualityException.forInvalidEnumDBValue(dbValue, AlbumType.class);
        }).orElse(null);
    }
}
