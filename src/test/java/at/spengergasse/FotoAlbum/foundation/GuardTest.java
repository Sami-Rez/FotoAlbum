package at.spengergasse.FotoAlbum.foundation;

import org.junit.jupiter.api.Test;

import static at.spengergasse.FotoAlbum.foundation.Guard.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GuardTest {

    @Test
    void ensureNotNullWithNonNullArgumentReturnsArgument() {
        assertThat(ensureNotNull("A")).isEqualTo("A");
    }

    @Test
    void ensureNotNullWithNullThrowsException() {
        var ex = assertThrows(IllegalArgumentException.class, () -> ensureNotNull(null));
        assertThat(ex).hasMessageContaining("'argument' must not be null!");
    }

    @Test
    void ensureNotNullWithNullAndNameThrowsException() {
        var ex = assertThrows(IllegalArgumentException.class, () -> ensureNotNull(null, "ABC"));
        assertThat(ex).hasMessageContaining("'ABC' must not be null!");
    }

    @Test
    void ensurePredicatesWork() {
        assertThat(isNotNull.test(null)).isFalse();
        assertThat(isNotNull.test("A")).isTrue();
        assertThat(isNull.test(null)).isTrue();
        assertThat(isNull.test("A")).isFalse();
    }
}