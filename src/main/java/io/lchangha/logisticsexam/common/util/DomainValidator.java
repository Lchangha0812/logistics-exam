package io.lchangha.logisticsexam.common.util;

import io.lchangha.logisticsexam.common.exception.DomainException;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public final class DomainValidator {

    private DomainValidator() {
        // Utility class
    }

    public static <T> T requireNonNull(T obj, Supplier<? extends DomainException> exceptionSupplier) {
        if (obj == null) {
            throw exceptionSupplier.get();
        }
        return obj;
    }

    public static String requireNonBlank(String text, Supplier<? extends DomainException> exceptionSupplier) {
        if (text == null || text.isBlank()) {
            throw exceptionSupplier.get();
        }
        return text;
    }

    public static void isTrue(boolean expression, Supplier<? extends DomainException> exceptionSupplier) {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    public static void isFalse(boolean expression, Supplier<? extends DomainException> exceptionSupplier) {
        if (expression) {
            throw exceptionSupplier.get();
        }
    }

    public static <T> Collection<T> requireNonEmpty(Collection<T> collection, Supplier<? extends DomainException> exceptionSupplier) {
        if (collection == null || collection.isEmpty()) {
            throw exceptionSupplier.get();
        }
        return collection;
    }
}
