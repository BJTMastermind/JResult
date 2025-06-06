package me.bjtmastermind.jresult;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import me.bjtmastermind.jresult.utils.FormatUtils;

public final class Ok<T, E> implements Result<T, E> {
    private final T value;

    public Ok(T value) {
        this.value = value;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isOkAnd(Function<T, Boolean> condition) {
        return isOk() && condition.apply(value) ? true : false;
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public boolean isErrAnd(Function<E, Boolean> condition) {
        return isErr() && condition.apply(null) ? true : false;
    }

    @Override
    public Optional<T> ok() {
        return Optional.of(value);
    }

    @Override
    public Optional<E> err() {
        return Optional.empty();
    }

    @Override
    public <U> Result<U, E> map(Function<T, U> f) {
        return new Ok<U, E>(f.apply(value));
    }

    @Override
    public <U> U mapOr(U defaultValue, Function<T, U> f) {
        return f.apply(value);
    }

    @Override
    public <U> U mapOrElse(Function<E, U> defaultValue, Function<T, U> f) {
        return f.apply(value);
    }

    @Override
    public <F> Result<T, F> mapErr(Function<E, F> f) {
        return new Ok<T, F>(value);
    }

    @Override
    public Result<T, E> inspect(Consumer<T> f) {
        f.accept(value);
        return this;
    }

    @Override
    public Result<T, E> inspectErr(Consumer<E> f) {
        return this;
    }

    @Override
    public Stream<T> iter() {
        return Stream.of(value);
    }

    @Override
    public T expect(String msg) {
        return value;
    }

    @Override
    public T unwrap() {
        return value;
    }

    @Override
    public E expectErr(String msg) {
        String format = "%s: %s";
        if (value instanceof String) {
            format = "%s: \"%s\"";
        }
        throw new RuntimeException(String.format(format, msg, value));
    }

    @Override
    public E unwrapErr() {
        throw new RuntimeException("called `Result.unwrapErr()` on an `Ok` value: " + FormatUtils.formatError(value));
    }

    @Override
    public <U> Result<U, E> and(Result<U, E> res) {
        return res;
    }

    @Override
    public <U> Result<U, E> andThen(Function<T, Result<U, E>> f) {
        return f.apply(value);
    }

    @Override
    public <F> Result<T, F> or(Result<T, F> res) {
        return new Ok<T, F>(value);
    }

    @Override
    public <F> Result<T, F> orElse(Function<E, Result<T, F>> f) {
        return new Ok<T, F>(value);
    }

    @Override
    public T unwrapOr(T defaultValue) {
        return value;
    }

    @Override
    public T unwrapOrElse(Function<E, T> f) {
        return value;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Ok ok = (Ok) obj;
        return value.equals(ok.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        String formatString = "Ok(%s)";
        if (value instanceof String) {
            formatString = "Ok(\"%s\")";
        }
        return String.format(formatString, value);
    }
}
