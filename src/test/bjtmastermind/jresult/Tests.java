package test.bjtmastermind.jresult;

import me.bjtmastermind.jresult.Result;
import test.bjtmastermind.jresult.Error.ErrorKind;
import me.bjtmastermind.jresult.Ok;
import me.bjtmastermind.jresult.Err;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class Tests {
    @Test
    void test_isOk() {
        Result<Integer, String> x = new Ok<>(-3);
        assertEquals(true, x.isOk());

        x = new Err<>("Some error message");
        assertEquals(false, x.isOk());
    }

    @Test
    void test_isOkAnd() {
        Result<Integer, String> x = new Ok<>(2);
        assertEquals(true, x.isOkAnd((y) -> y > 1));

        x = new Ok<>(0);
        assertEquals(false, x.isOkAnd((y) -> y > 1));

        x = new Err<>("hey");
        assertEquals(false, x.isOkAnd((y) -> y > 1));
    }

    @Test
    void test_isErr() {
        Result<Integer, String> x = new Ok<>(-3);
        assertEquals(false, x.isErr());

        x = new Err<>("Some error message");
        assertEquals(true, x.isErr());
    }

    @Test
    void test_isErrAnd() {
        Result<Integer, Error> x = new Err<>(new Error(ErrorKind.NotFound));
        assertEquals(true, x.isErrAnd((y) -> y.kind() == ErrorKind.NotFound));

        x = new Err<>(new Error(ErrorKind.PermissionDenied));
        assertEquals(false, x.isErrAnd((y) -> y.kind() == ErrorKind.NotFound));

        x = new Ok<>(123);
        assertEquals(false, x.isErrAnd((y) -> y.kind() == ErrorKind.NotFound));
    }

    @Test
    void test_ok() {
        Result<Integer, String> x = new Ok<>(2);
        assertEquals(Optional.of(2), x.ok());

        x = new Err<>("Nothing here");
        assertEquals(Optional.empty(), x.ok());
    }

    @Test
    void test_err() {
        Result<Integer, String> x = new Ok<>(2);
        assertEquals(Optional.empty(), x.err());

        x = new Err<>("Nothing here");
        assertEquals(Optional.of("Nothing here"), x.err());
    }

    @Test
    void test_map() {
        String line = "1\n2\n3\n4\n";

        for (String num : line.split("\n")) {
            Result<Integer, Exception> x = Result.capture(() -> Integer.parseInt(num)).map((i) -> i * 2);
            if (x.isOk()) {
                x.inspect((i) -> System.out.println(i));
            }
        }
    }

    @Test
    void test_mapOr() {
        Result<String, String> x = new Ok<>("foo");
        assertEquals(3, x.mapOr(42, (v) -> v.length()));

        x = new Err<>("bar");
        assertEquals(42, x.mapOr(42, (v) -> v.length()));
    }

    @Test
    void test_mapOrElse() {
        int k = 21;

        Result<String, String> x = new Ok<>("foo");
        assertEquals(3, x.mapOrElse((e) -> k * 2, (v) -> v.length()));

        x = new Err<>("bar");
        assertEquals(42, x.mapOrElse((e) -> k * 2, (v) -> v.length()));
    }

    @Test
    void test_mapErr() {
        Result<Integer, Integer> x = new Ok<>(2);
        assertEquals(new Ok<>(2), x.mapErr((y) -> stringify(y)));

        x = new Err<>(13);
        assertEquals(new Err<>("error code: 13"), x.mapErr((y) -> stringify(y)));
    }

    @Test
    void test_inspect() {
        Result<Byte, String> x = new Ok<>((byte) 4);
        x.inspect((y) -> System.out.println("original: " + y))
        .map((y) -> Math.pow(y, 3.0))
        .expect("failed to parse number");
    }

    @Test
    void test_inspectErr() {
        Result<List<String>, Exception> x = Result.capture(() -> Files.readAllLines(Paths.get(new File("address.txt").toURI())));
        x.inspectErr((e) -> System.err.println("failed to read file: "+ e));
    }

    @Test
    void test_iter() {
        Result<Integer, String> x = new Ok<>(7);
        assertEquals(7, x.iter().iterator().next());

        x = new Err<>("nothing");
        assertEquals(0, x.iter().count());
    }

    @Test
    void test_expect() {
        assertThrows(RuntimeException.class, () -> {
            Result<Integer, String> x = new Err<>("emergency failure");
            x.expect("Testing expect"); // errors with `Testing expect: emergency failure`
        });
    }

    @Test
    void test_unwrap() {
        Result<Integer, String> x = new Ok<>(2);
        assertEquals(2, x.unwrap());

        assertThrows(RuntimeException.class, () -> {
            Result<Integer, String> y = new Err<>("emergency failure");
            y.unwrap();
        });
    }

    @Test
    void test_expectErr() {
        assertThrows(RuntimeException.class, () -> {
            Result<Integer, String> x = new Ok<>(10);
            x.expectErr("Testing expectErr"); // errors with `Testing expectErr: 10`
        });
    }

    @Test
    void test_unwrapErr() {
        assertThrows(RuntimeException.class, () -> {
            Result<Integer, String> x = new Ok<>(2);
            x.unwrapErr(); // errors with `2`
        });

        Result<Integer, String> x = new Err<>("emergency failure");
        assertEquals("emergency failure", x.unwrapErr());
    }

    @Test
    void test_and() {
        Result<Integer, String> x = new Ok<>(2);
        Result<String, String> y = new Err<>("late error");
        assertEquals(new Err<>("late error"), x.and(y));

        x = new Err<>("early error");
        y = new Ok<>("foo");
        assertEquals(new Err<>("early error"), x.and(y));

        x = new Err<>("not a 2");
        y = new Err<>("late error");
        assertEquals(new Err<>("not a 2"), x.and(y));

        x = new Ok<>(2);
        y = new Ok<>("different result type");
        assertEquals(new Ok<>("different result type"), x.and(y));
    }

    @Test
    void test_andThen() {
        assertEquals(new Ok<>("4"), new Ok<Integer, String>(2).andThen((x) -> sqThenToString(x)));
        assertEquals(new Err<>("overflowed"), new Ok<Integer, String>(1_000_000).andThen((x) -> sqThenToString(x)));
        assertEquals(new Err<>("not a number"), new Err<Integer, String>("not a number").andThen((x) -> sqThenToString(x)));
    }

    @Test
    void test_or() {
        Result<Integer, String> x = new Ok<>(2);
        Result<Integer, String> y = new Err<>("late error");
        assertEquals(new Ok<>(2), x.or(y));

        x = new Err<>("early error");
        y = new Ok<>(2);
        assertEquals(new Ok<>(2), x.or(y));

        x = new Err<>("not a 2");
        y = new Err<>("late error");
        assertEquals(new Err<>("late error"), x.or(y));

        x = new Ok<>(2);
        y = new Ok<>(100);
        assertEquals(new Ok<>(2), x.or(y));
    }

    @Test
    void test_orElse() {
        assertEquals(new Ok<>(2), new Ok<Integer, Integer>(2).orElse((x) -> sq(x)).orElse((x) -> sq(x)));
        assertEquals(new Ok<>(2), new Ok<Integer, Integer>(2).orElse((x) -> err(x)).orElse((x) -> sq(x)));
        assertEquals(new Ok<>(9), new Err<Integer, Integer>(3).orElse((x) -> sq(x)).orElse((x) -> err(x)));
        assertEquals(new Err<>(3), new Err<Integer, Integer>(3).orElse((x) -> err(x)).orElse((x) -> err(x)));
    }

    @Test
    void test_unwrapOr() {
        int defaultValue = 2;
        Result<Integer, String> x = new Ok<>(9);
        assertEquals(9, x.unwrapOr(defaultValue));
    }

    @Test
    void test_unwrapOrElse() {
        assertEquals(2, new Ok<Integer, String>(2).unwrapOrElse((x) -> count(x)));
        assertEquals(3, new Err<Integer, String>("foo").unwrapOrElse((x) -> count(x)));
    }

    @Test
    void test_captureSupplier() {
        Result<Integer, Exception> x = Result.capture(() -> methodThatCanThrow(10));
        assertEquals(new Ok<>(10), x);

        assertThrows(Exception.class, () -> {
            Result<Integer, Exception> y = Result.capture(() -> methodThatCanThrow(0));
            y.unwrap();
        });
    }

    @Test
    void test_captureRunnable() {
        Result<Void, Exception> x = Result.capture(() -> methodThatCanThrow(false));
        assertEquals(null, x.unwrap());

        assertThrows(Exception.class, () -> {
            Result<Void, Exception> y = Result.capture(() -> methodThatCanThrow(true));
            y.unwrap();
        });
    }

    private Result<String, String> sqThenToString(int x) {
        Optional<String> sqOut = checkedMul(x, x).map((sq) -> sq.toString());
        if (sqOut.isPresent()) {
            return new Ok<>(sqOut.get());
        } else {
            return new Err<>("overflowed");
        }
    }

    private Optional<Integer> checkedMul(int x, int y) {
        try {
            int squared = Math.multiplyExact(x, y);
            return Optional.of(squared);
        } catch (ArithmeticException e) {
            return Optional.empty();
        }
    }

    private String stringify(int x) {
        return String.format("error code: %d", x);
    }

    private Result<Integer, Integer> sq(int x) {
        return new Ok<>(x * x);
    }

    private Result<Integer, Integer> err(int x) {
        return new Err<>(x);
    }

    private int count(String x) {
        return x.length();
    }

    private int methodThatCanThrow(int x) throws Exception {
        if (x == 0) {
            throw new Exception("0 was entered!");
        }
        return x;
    }

    private void methodThatCanThrow(boolean x) throws Exception {
        if (x) {
            throw new Exception("0 was entered!");
        }
    }
}
