# JResult

A Java implementation of the Rust Result type 

## How To Use

1. Download `jresult-v0.1.0.jar` from the [Releases]() page.
2. Place the jar in your projects `lib` folder.

* Import the Result type with `import me.bjtmastermind.jresult.Result;`
* Import the Ok type with `import me.bjtmastermind.jresult.Ok;`
* Import the Err type with `import me.bjtmastermind.jresult.Err;`

You can capture the output of a method and turn it into a Result type with:
```java
Result<T, Exception> result = Result.capture(() -> methodThatThrows(arg1, arg2));
```

`T` being the type that `methodThatThrows` returns.

If `methodThatThrows` doesn't return anything `T` would be set to `Void`.

## Language(s) Used

* Java 17
