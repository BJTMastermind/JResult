package me.bjtmastermind.jresult;

import java.util.Iterator;
import java.util.Optional;

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
    public boolean isOkAnd() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOkAnd'");
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public boolean isErrAnd() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isErrAnd'");
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
    public <U, F> Result<U, E> map(F op) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }

    @Override
    public <U, F> Result<U, E> mapOr(U defaultValue, F f) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapOr'");
    }

    @Override
    public <U, D, F> U mapOrElse(D defaultValue, F f) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapOrElse'");
    }

    @Override
    public <F, O> Result<T, E> mapErr(O op) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapErr'");
    }

    @Override
    public <F> Result<T, E> inspect(F f) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inspect'");
    }

    @Override
    public <F> Result<T, E> inspectErr(F f) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inspectErr'");
    }

    @Override
    public Iterator<T> iter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iter'");
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
    public T unwrapOrDefault() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unwrapOrDefault'");
    }

    @Override
    public E expectErr(String msg) {
        if (value instanceof String) {
            throw new RuntimeException(msg + ": \"" + value + "\"");
        }
        throw new RuntimeException(msg + ": " + value);
    }

    @Override
    public E unwrapErr() {
        throw new RuntimeException("called `Result.unwrapErr()` on an `Ok` value: " + value);
    }

    @Override
    public <U> Result<U, E> and(Result<U, E> res) {
        return res;
    }

    @Override
    public <U, F> Result<U, E> andThen(F op) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'andThen'");
    }

    @Override
    public <F> Result<T, F> or(Result<T, F> res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'or'");
    }

    @Override
    public <F, O> Result<T, F> orElse(O op) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }

    @Override
    public T unwrapOr(T defaultValue) {
        return value;
    }

    @Override
    public <F> T unwrapOrElse(F op) {
        return value;
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
