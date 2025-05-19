# JResult

A Java implementation of the Rust Result type

## How To Use

1. Download `jresult-v0.1.1.jar` from the [Releases](https://github.com/BJTMastermind/JResult/releases) page.
2. Place the jar in your projects `lib` folder.

* Import the Result type with `import me.bjtmastermind.jresult.Result;`
* Import the Ok type with `import me.bjtmastermind.jresult.Ok;`
* Import the Err type with `import me.bjtmastermind.jresult.Err;`

You can create a Result of an Ok type with:
```java
Result<T, E> result = new Ok<T, E>(value);
```

and an Err type with:
```java
Result<T, E> result = new Err<T, E>(value);
```

`T` is your successful value type and `E` is your error type with `value` being the Ok or Err value.

You can also capture the output of a method and turn it into a Result type with:
```java
Result<T, Exception> result = Result.capture(() -> methodThatThrows(arg1, arg2));
```

`T` being the type that `methodThatThrows` returns.

If `methodThatThrows` doesn't return anything `T` would be set to `Void`.

## Language(s) Used

* Java 17
