package at.spengergasse.FotoAlbum.foundation;

import java.util.function.Predicate;

public class Guard {

    public static final Predicate<? super Object> isNull = arg -> arg == null;
    public static final Predicate<? super Object> isNotNull = isNull.negate();

    public static <T> T ensureNotNull(T argument) {
        return ensureNotNull(argument, "argument");
    }

    public static <T> T ensureNotNull(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException("'%s' must not be null!".formatted(name));
        }

        return argument;
    }
}
