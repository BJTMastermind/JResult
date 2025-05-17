package me.bjtmastermind.jresult;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Err<T, E> implements Result<T, E> {
    private final E error;

    public Err(E error) {
        this.error = error;
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public boolean isOkAnd(Function<T, Boolean> condition) {
        return isOk() && condition.apply(null) ? true : false;
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public boolean isErrAnd(Function<E, Boolean> condition) {
        return isErr() && condition.apply(error) ? true : false;
    }

    @Override
    public Optional<T> ok() {
        return Optional.empty();
    }

    @Override
    public Optional<E> err() {
        return Optional.of(error);
    }

    @Override
    public <U, F> Result<U, E> map(Function<T, U> f) {
        return new Err<U, E>(error);
    }

    @Override
    public <U, F> U mapOr(U defaultValue, Function<T, U> f) {
        return defaultValue;
    }

    @Override
    public <U> U mapOrElse(Function<E, U> defaultValue, Function<T, U> f) {
        return defaultValue.apply(error);
    }

    @Override
    public <O> Result<T, O> mapErr(Function<E, O> f) {
        return new Err<T, O>(f.apply(error));
    }

    @Override
    public <F> Result<T, E> inspect(Consumer<T> f) {
        return this;
    }

    @Override
    public <F> Result<T, E> inspectErr(Consumer<E> f) {
        f.accept(error);
        return this;
    }

    @Override
    public Stream<T> iter() {
        return Stream.empty();
    }

    @Override
    public T expect(String msg) {
        unwrapFailed(msg, error);
        return null;
    }

    @Override
    public T unwrap() {
        if (error instanceof String) {
            throw new RuntimeException("called `Result.unwrap()` on an `Err` value: \"" + error + "\"");
        }
        throw new RuntimeException("called `Result.unwrap()` on an `Err` value: " + error.getClass().getSimpleName() + " { " + error.toString().replace(error.getClass().getName()+": ", "message: \"") + "\" }");
    }

    // @Override
    // public T unwrapOrDefault() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'unwrapOrDefault'");
    // }

    @Override
    public E expectErr(String msg) {
        return error;
    }

    @Override
    public E unwrapErr() {
        return error;
    }

    @Override
    public <U> Result<U, E> and(Result<U, E> res) {
        return new Err<U, E>(error);
    }

    @Override
    public <U> Result<U, E> andThen(Function<T, Result<U, E>> f) {
        return new Err<U, E>(error);
    }

    @Override
    public <F> Result<T, F> or(Result<T, F> res) {
        return res;
    }

    @Override
    public <F> Result<T, F> orElse(Function<E, Result<T, F>> f) {
        return f.apply(error);
    }

    @Override
    public T unwrapOr(T defaultValue) {
        return defaultValue;
    }

    @Override
    public T unwrapOrElse(Function<E, T> f) {
        return f.apply(error);
    }

    private void unwrapFailed(String msg, E error) {
        String format = "%s: %s";
        if (error instanceof String) {
            format = "%s: \"%s\"";
        }
        throw new RuntimeException(String.format(format, msg, error));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Err err = (Err) obj;
        return error.equals(err.error);
    }

    @Override
    public int hashCode() {
        return error.hashCode();
    }

    @Override
    public String toString() {
        String formatString = "Err(%s)";
        if (error instanceof String) {
            formatString = "Err(\"%s\")";
        }
        return String.format(formatString, error);
    }
}
