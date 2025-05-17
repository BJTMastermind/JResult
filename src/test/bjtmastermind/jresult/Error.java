package test.bjtmastermind.jresult;

public class Error {
    private ErrorKind kind;

    public enum ErrorKind {
        NotFound,
        PermissionDenied;
    }

    public Error(ErrorKind kind) {
        this.kind = kind;
    }

    public ErrorKind kind() {
        return kind;
    }
}
