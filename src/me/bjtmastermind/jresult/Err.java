package me.bjtmastermind.jresult;

import java.util.Iterator;
import java.util.Optional;

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
    public boolean isOkAnd() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOkAnd'");
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public boolean isErrAnd() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isErrAnd'");
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

    @Override
    public T unwrapOrDefault() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unwrapOrDefault'");
    }

    @Override
    public E expectErr(String msg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'expectErr'");
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
        return defaultValue;
    }

    @Override
    public <F> T unwrapOrElse(F op) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unwrapOrElse'");
    }

    private void unwrapFailed(String msg, E error) {
        String format = "%s: %s";
        if (error instanceof String) {
            format = "%s: \"%s\"";
        }
        throw new RuntimeException(String.format(format, msg, error));
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
