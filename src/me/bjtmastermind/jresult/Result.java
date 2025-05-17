package me.bjtmastermind.jresult;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public sealed interface Result<T, E> permits Ok, Err {
    public boolean isOk();

    public boolean isOkAnd(boolean condition);

    public boolean isErr();

    public boolean isErrAnd(boolean condition);

    public Optional<T> ok();

    public Optional<E> err();

    public <U, F> Result<U, E> map(Function<T, U> f);

    public <U, F> U mapOr(U defaultValue, Function<T, U> f);

    public <U> U mapOrElse(Function<E, U> defaultValue, Function<T, U> f);

    public <O> Result<T, O> mapErr(Function<E, O> f);

    public <F> Result<T, E> inspect(Consumer<T> f);

    public <F> Result<T, E> inspectErr(Consumer<E> f);

    public Stream<T> iter();

    public T expect(String msg);

    public T unwrap();

    // public T unwrapOrDefault();

    public E expectErr(String msg);

    public E unwrapErr();

    public <U> Result<U, E> and(Result<U, E> res);

    public <U> Result<U, E> andThen(Function<T, Result<U, E>> f);

    public <F> Result<T, F> or(Result<T, F> res);

    public <F> Result<T, F> orElse(Function<E, F> f);

    public T unwrapOr(T defaultValue);

    public T unwrapOrElse(Function<E, T> f);

    public static <T> Result<T, Exception> capture(ThrowingSupplier<T> supplier) {
        try {
            return new Ok<T, Exception>(supplier.get());
        } catch (Exception e) {
            return new Err<T, Exception>(e);
        }
    }

    public static <T> Result<T, Exception> capture(ThrowingRunnable<T> runnable) {
        try {
            runnable.run();
            return new Ok<T, Exception>(null);
        } catch (Exception e) {
            return new Err<T, Exception>(e);
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
