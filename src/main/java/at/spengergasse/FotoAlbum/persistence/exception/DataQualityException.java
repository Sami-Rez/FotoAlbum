package at.spengergasse.FotoAlbum.persistence.exception;

public class DataQualityException extends RuntimeException {

    private DataQualityException(String message) {
        super(message);
    }

    public static DataQualityException forInvalidEnumDBValue(String dbValue, Class<? extends Enum> enumType) {
        String msg = "Unknown dbValue of '%s' for enumType '%s'".formatted(dbValue, enumType.getSimpleName());
        return new DataQualityException(msg);
    }
}
