package me.bjtmastermind.jresult;

import java.util.Iterator;
import java.util.Optional;

public sealed interface Result<T, E> permits Ok, Err {
    public boolean isOk();

    public boolean isOkAnd();

    public boolean isErr();

    public boolean isErrAnd();

    public Optional<T> ok();

    public Optional<E> err();

    public <U, F> Result<U, E> map(F op);

    public <U, F> Result<U, E> mapOr(U defaultValue, F f);

    public <U, D, F> U mapOrElse(D defaultValue, F f);

    public <F, O> Result<T, E> mapErr(O op);

    public <F> Result<T, E> inspect(F f);

    public <F> Result<T, E> inspectErr(F f);

    public Iterator<T> iter();

    public T expect(String msg);

    public T unwrap();

    public T unwrapOrDefault();

    public E expectErr(String msg);

    public E unwrapErr();

    public <U> Result<U, E> and(Result<U, E> res);

    public <U, F> Result<U, E> andThen(F op);

    public <F> Result<T, F> or(Result<T, F> res);

    public <F, O> Result<T, F> orElse(O op);

    public T unwrapOr(T defaultValue);

    public <F> T unwrapOrElse(F op);

    @SuppressWarnings("unchecked")
    public static <T, E> Result<T, E> capture(ThrowingSupplier<T> supplier) {
        try {
            return new Ok<T,E>(supplier.get());
        } catch (Exception e) {
            return new Err<T, E>((E) e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E> Result<T, E> capture(ThrowingRunnable<T> runnable) {
        try {
            runnable.run();
            return new Ok<T,E>(null);
        } catch (Exception e) {
            return new Err<T, E>((E) e);
        }
    }

    @FunctionalInterface
    interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    interface ThrowingRunnable<T> {
        void run() throws Exception;
    }
}
