package at.spengergasse.FotoAlbum.persistence.converter;


import at.spengergasse.FotoAlbum.domain.Orientation;
import at.spengergasse.FotoAlbum.persistence.exception.DataQualityException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class OrientationConverter implements AttributeConverter<Orientation, String> {
    @Override
    public String convertToDatabaseColumn(Orientation orientation) {
        return Optional.ofNullable(orientation)
                       .map(o -> switch (o) {
                           case LANDSCAPE -> "L";
                           case PORTRAIT -> "P";
                           case SQUARE -> "S";
                       }).orElse(null);
    }

    @Override
    public Orientation convertToEntityAttribute(String dbValue) {
        return Optional.ofNullable(dbValue)
                       .map(v -> switch (v) {
                           case "L" -> Orientation.LANDSCAPE;
                           case "P" -> Orientation.PORTRAIT;
                           case "S" -> Orientation.SQUARE;
                           default -> throw DataQualityException.forInvalidEnumDBValue(dbValue, Orientation.class);
                       }).orElse(null);
    }
}
