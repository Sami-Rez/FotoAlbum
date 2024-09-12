package at.spengergasse.FotoAlbum.foundation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StrongPasswordValidatorTest {

    private StrongPasswordValidator validator = new StrongPasswordValidator();
    private @Mock ConstraintValidatorContext ctx;

    @ParameterizedTest
    @ValueSource(strings = {
            "!SehrSicher2024",
            "_UltraGeheim123"
    })
    void ensureStrongPasswordAreValidatedAsValid(String password) {
        assertThat(validator.isValid(password, ctx)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "SehrSicher2024",
            "AAAAAAAAAAAAAA",
            "aaaaaaaaaaaaaa",
            "123412341234",
            "_Super2024",
    })
    void ensureWeakPasswordAreValidatedAsInvalid(String password) {
        assertThat(validator.isValid(password, ctx)).isFalse();
    }

}