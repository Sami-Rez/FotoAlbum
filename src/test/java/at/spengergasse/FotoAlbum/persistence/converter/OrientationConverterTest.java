package at.spengergasse.FotoAlbum.persistence.converter;


import at.spengergasse.FotoAlbum.domain.Orientation;
import at.spengergasse.FotoAlbum.persistence.exception.DataQualityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrientationConverterTest {

    private OrientationConverter converter;

    @BeforeEach
    void setup() {
        converter = new OrientationConverter();
    }

    @Test
    void ensureConvertToDatabaseColumnWorks() {
        // expect
        assertThat(converter.convertToDatabaseColumn(Orientation.PORTRAIT)).isEqualTo("P");
        assertThat(converter.convertToDatabaseColumn(Orientation.LANDSCAPE)).isEqualTo("L");
        assertThat(converter.convertToDatabaseColumn(Orientation.SQUARE)).isEqualTo("S");
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    void ensureConvertToEntityAttributeWorksForValidValues() {
        // expect
        assertThat(converter.convertToEntityAttribute("P")).isEqualTo(Orientation.PORTRAIT);
        assertThat(converter.convertToEntityAttribute("L")).isEqualTo(Orientation.LANDSCAPE);
        assertThat(converter.convertToEntityAttribute("S")).isEqualTo(Orientation.SQUARE);
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    void ensureConvertToEntityAttributeThrowsAnExceptinoForInvalidValues() {
        // expect
        var iaEx = assertThrows(DataQualityException.class, () -> {
            converter.convertToEntityAttribute("X");
        });
        assertThat(iaEx).hasMessage("Unknown dbValue of 'X' for enumType 'Orientation'");
    }
}