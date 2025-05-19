package me.bjtmastermind.jresult;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public sealed interface Result<T, E> permits Ok, Err {
    /**
     * Returns {@code true} if the result is {@link Ok <code>Ok</code>}.
     *
     * @return {@code true} if the result is {@link Ok <code>Ok</code>}.
     */
    public boolean isOk();

    /**
     * <p>Returns {@code true} if the result is {@link Ok <code>Ok</code>} and the value inside of it matches a predicate.</p>
     *
     * @param condition A predicate to test the value inside the {@link Ok <code>Ok</code>}.
     * @return {@code true} if the result is {@link Ok <code>Ok</code>} and the value matches a predicate.
     */
    public boolean isOkAnd(Function<T, Boolean> condition);

    /**
     * <p>Returns {@code true} if the result is {@link Err <code>Err</code>}.</p>
     *
     * @return {@code true} if the result is {@link Err <code>Err</code>}.
     */
    public boolean isErr();

    /**
     * <p>Returns {@code true} if the result is {@link Err <code>Err</code>} and the value inside of it matches a predicate.</p>
     *
     * @param condition A predicate to test the value inside the {@link Err <code>Err</code>}.
     * @return {@code true} if the result is {@link Err <code>Err</code>} and the value matches a predicate.
     */
    public boolean isErrAnd(Function<E, Boolean> condition);

    /**
     * <p>Converts from {@code Result<T, E>} to {@link Optional <code>Optional&lt;T&gt;</code>}.</p>
     *
     * <p>Converts {@code this} into an {@link Optional <code>Optional&lt;T&gt;</code>}, consuming {@code this}, and discarding the error, if any.</p>
     *
     * @return {@link Optional <code>Optional&lt;T&gt;</code>} containing the success value, if any.
     */
    public Optional<T> ok();

    /**
     * <p>Converts from {@code Result<T, E>} to {@link Optional <code>Optional&lt;E&gt;</code>}.</p>
     *
     * <p>Converts {@code this} into an {@link Optional <code>Optional&lt;E&gt;</code>}, consuming {@code this}, and discarding the success value, if any.</p>
     *
     * @return {@link Optional <code>Optional&lt;E&gt;</code>} containing the error value, if any.
     */
    public Optional<E> err();

    /**
     * <p>Maps a {@code Result<T, E>} to {@code Result<U, E>} by applying a function to a contained {@link Ok <code>Ok</code>} value, leaving an {@link Err <code>Err</code>} value untouched.</p>
     *
     * <p>This function can be used to compose the results of two functions.</p>
     *
     * @param <U> <b>U</b> - The sussess type of the new {@code Result}.
     * @param f - The function.
     * @return The result of applying a function to a contained {@link Ok <code>Ok</code>} value.
     */
    public <U> Result<U, E> map(Function<T, U> f);

    /**
     * <p>Returns the provided default (if {@link Err <code>Err</code>}), or applies a function to the contained value (if {@link Ok <code>Ok</code>}).</p>
     *
     * <p>Arguments passed to {@code mapOr} are eagerly evaluated; if you are passing the result of a function call, it is recommended to use {@link #mapOrElse <code>mapOrElse</code>}, which is lazily evaluated.</p>
     *
     * @param <U> <b>U</b> - The default value type.
     * @param defaultValue - The default value.
     * @param f - The function.
     * @return The provided default (if {@link Err <code>Err</code>}), or applies a function to the contained value (if {@link Ok <code>Ok</code>}).
     */
    public <U> U mapOr(U defaultValue, Function<T, U> f);

    /**
     * <p>Maps a {@code Result<T, E>} to {@code U} by applying fallback function {@code defaultValue} to a contained {@link Err <code>Err</code>} value, or function {@code f} to a contained {@link Ok <code>Ok</code>} value.</p>
     *
     * <p>This function can be used to unpack a successful result while handling an error.</p>
     *
     * @param <U> <b>U</b> - The type to be returned.
     * @param defaultValue - The fallback function.
     * @param f - The function.
     * @return The result of applying fallback function {@code defaultValue} to a contained {@link Err <code>Err</code>} value, or function {@code f} to a contained {@link Ok <code>Ok</code>} value.
     */
    public <U> U mapOrElse(Function<E, U> defaultValue, Function<T, U> f);

    /**
     * <p>Maps a {@code Result<T, E>} to {@code Result<T, F>} by applying a function to a contained {@link Err <code>Err</code>} value, leaving an {@link Ok <code>Ok</code>} value untouched.</p>
     *
     * <p>This function can be used to pass through a successful result while handling an error.</p>
     *
     * @param <F> <b>F</b> - The error type of the new {@code Result}.
     * @param f - The function.
     * @return The result of applying a function to a contained {@link Err <code>Err</code>} value.
     */
    public <F> Result<T, F> mapErr(Function<E, F> f);

    /**
     * <p>Calls a function with a reference to the contained value if {@link Ok <code>Ok</code>}.</p>
     *
     * <p>Returns the original result.</p>
     *
     * @param f - What to run on the value.
     * @return The original result.
     */
    public Result<T, E> inspect(Consumer<T> f);

    /**
     * <p>Calls a function with a reference to the contained value if {@link Err <code>Err</code>}.</p>
     *
     * <p>Returns the original result.</p>
     *
     * @param f - What to run on the value.
     * @return The original result.
     */
    public Result<T, E> inspectErr(Consumer<E> f);

    /**
     * <p>Returns an iterator over the possibly contained value.</p>
     *
     * <p>The iterator yields one value if the result is {@link Ok <code>Ok</code>}, otherwise none.</p>
     *
     * @return The iterator.
     */
    public Stream<T> iter();

    /**
     * <p>Returns the contained {@link Ok <code>Ok</code>} value, consuming the {@code this} value.</p>
     *
     * <p>Because this function may throw, its use is generally discouraged. Instead, prefer to use pattern matching
     * and handle the {@link Err <code>Err</code>} case explicity, or call
     * {@link #unwrapOr <code>unwrapOr</code>} or {@link #unwrapOrElse <code>unwrapOrElse</code>}.</p>
     *
     * <b>Throws</b>
     *
     * <p>Throws if the value is an {@link Err <code>Err</code>}, with a throw message including the passed message,
     * and the content of the {@link Err <code>Err</code>}.</p>
     *
     * @param msg - The error message.
     * @return The contained value.
     */
    public T expect(String msg);

    /**
     * <p>Returns the contained {@link Ok <code>Ok</code>} value, consuming the {@code this} value.</p>
     *
     * <p>Because this function may throw, its use is generally discouraged. Throws are meant for unrecoverable errors,
     * and may abort the entire program.</p>
     *
     * <p>Instead, prefer to use pattern matching to handle the {@link Err <code>Err</code>} case explicitly,
     * or call {@link #unwrapOr <code>unwrapOr</code>} or {@link #unwrapOrElse <code>unwrapOrElse</code>}.</p>
     *
     * <b>Throws</b>
     *
     * <p>Throws if the value is an {@link Err <code>Err</code>}, with a throw message provided by the {@link Err <code>Err</code>}'s value.</p>
     *
     * @return The contained value.
     */
    public T unwrap();

    /**
     * <p>Returns the contained {@link Err <code>Err</code>} value, consuming the {@code this} value.</p>
     *
     * <b>Throws</b>
     *
     * <p>Throws if the value is an {@link Ok <code>Ok</code>}, with a throw message including the passed message,
     * and the content of the {@link Ok <code>Ok</code>}.</p>
     *
     * @param msg - The error message.
     * @return The contained value.
     */
    public E expectErr(String msg);

    /**
     * <p>Returns the contained {@link Err <code>Err</code>} value, consuming the {@code this} value.</p>
     *
     * <b>Throws</b>
     *
     * <p>Throws if the value is an {@link Ok <code>Ok</code>}, with a throw message provided by the {@link Ok <code>Ok</code>}'s value.</p>
     *
     * @return The contained value.
     */
    public E unwrapErr();

    /**
     * <p>Returns {@code res} if the result is {@link Ok <code>Ok</code>}, otherwise returns the {@link Err <code>Err</code>} value of {@code this}.</p>
     *
     * <p>Arguments passed to {@code and} are eagerly evaluated; if you are passing the result of a function call, it is recommended to use {@link #andThen <code>andThen</code>} which is lazily evaluated.</p>
     *
     * @param <U> <b>U</b> - The success type of the new {@code Result}.
     * @param res - The result to return if the current result is {@link Ok <code>Ok</code>}.
     * @return The provided result if the current result is {@link Ok <code>Ok</code>}.
     */
    public <U> Result<U, E> and(Result<U, E> res);

    /**
     * <p>Calls {@code op} if the result is {@link Ok <code>Ok</code>}, otherwise returns the {@link Err <code>Err</code>} value of {@code this}.</p>
     *
     * <p>This function can be used for control flow based on {@code Result} values.</p>
     *
     * @param <U> <b>U</b> - The success type of the new {@code Result}.
     * @param op - The function to call if the current result is {@link Ok <code>Ok</code>}.
     * @return The provided result if the current result is {@link Ok <code>Ok</code>}.
     */
    public <U> Result<U, E> andThen(Function<T, Result<U, E>> op);

    /**
     * <p>Returns {@code res} if the result is {@link Err <code>Err</code>}, otherwise returns the {@link Ok <code>Ok</code>} value of {@code this}.</p>
     *
     * <p>Arguments passed to {@code or} are eagerly evaluated; if you are passing the result of a function call, it is recommended to use {@link #orElse <code>orElse</code>} which is lazily evaluated.</p>
     *
     * @param <F> <b>F</b> - The error type of the new {@code Result}.
     * @param res - The result to return if the current result is {@link Err <code>Err</code>}.
     * @return The provided result if the current result is {@link Err <code>Err</code>}.
     */
    public <F> Result<T, F> or(Result<T, F> res);

    /**
     * <p>Calls {@code op} if the result is {@link Err <code>Err</code>}, otherwise returns the {@link Ok <code>Ok</code>} value of {@code this}.</p>
     *
     * <p>This function can be used for control flow based on {@code Result} values.</p>
     *
     * @param <F> <b>F</b> - The error type of the new {@code Result}.
     * @param f - The function to call if the current result is {@link Err <code>Err</code>}.
     * @return The provided result if the current result is {@link Err <code>Err</code>}.
     */
    public <F> Result<T, F> orElse(Function<E, Result<T, F>> f);

    /**
     * <p>Returns the contained {@link Ok <code>Ok</code>} value or a provided default.</p>
     *
     * <p>Arguments passed to {@code unwrapOr} are eagerly evaluated; if you are passing the result of a function call,
     * it is recommended to use {@link #unwrapOrElse <code>unwrapOrElse</code>} which is lazily evaluated.</p>
     *
     * @param defaultValue - The default value.
     * @return The contained value if {@link Ok <code>Ok</code>}, otherwise {@code defaultValue}.
     */
    public T unwrapOr(T defaultValue);

    /**
     * <p>Returns the contained {@link Ok <code>Ok</code>} value or computes it from a closure.</p>
     *
     * @param f - The closure to compute the default value.
     * @return The contained value if {@link Ok <code>Ok</code>}, otherwise the result of the closure.
     */
    public T unwrapOrElse(Function<E, T> f);

    /**
     * <p>Calls {@code supplier} and returns {@link Ok <code>Ok</code>} if execution succeeds, otherwise returns {@link Err <code>Err</code>} containing the thrown exception.</p>
     *
     * @param <T> <b>T</b> - The success type of the new {@code Result}.
     * @param supplier - The supplier to execute.
     * @return A new {@code Result} containing the return value, or the exception thrown by {@code supplier}.
     */
    public static <T> Result<T, Exception> capture(ThrowingSupplier<T> supplier) {
        try {
            return new Ok<T, Exception>(supplier.get());
        } catch (Exception e) {
            return new Err<T, Exception>(e);
        }
    }

    /**
     * <p>Calls {@code runnable} and returns {@link Ok <code>Ok</code>} if execution succeeds, otherwise returns {@link Err <code>Err</code>} containing the thrown exception.</p>
     *
     * @param runnable - The runnable to execute.
     * @return A new {@code Result} containing the exception thrown by {@code runnable}.
     */
    public static Result<Void, Exception> capture(ThrowingRunnable<Void> runnable) {
        try {
            runnable.run();
            return new Ok<Void, Exception>(null);
        } catch (Exception e) {
            return new Err<Void, Exception>(e);
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
