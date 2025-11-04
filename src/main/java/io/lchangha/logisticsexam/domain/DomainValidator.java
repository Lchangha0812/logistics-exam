package io.lchangha.logisticsexam.domain;

import java.util.Collection;
import java.util.function.Supplier;

public final class DomainValidator {

    private DomainValidator() {
        // Utility class
    }

    public static <T> T requireNonNull(T obj, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (obj == null) {
            throw exceptionSupplier.get();
        }
        return obj;
    }

    public static String requireNonBlank(String text, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (text == null || text.isBlank()) {
            throw exceptionSupplier.get();
        }
        return text;
    }

    public static void isTrue(boolean expression, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    public static void isFalse(boolean expression, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (expression) {
            throw exceptionSupplier.get();
        }
    }

    public static <C extends Collection<T>, T> C requireNonEmpty(C collection, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (collection == null || collection.isEmpty()) {
            throw exceptionSupplier.get();
        }
        return collection;
    }
}
