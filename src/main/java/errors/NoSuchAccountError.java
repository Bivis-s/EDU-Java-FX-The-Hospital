package errors;

public class NoSuchAccountError extends Error {
    public NoSuchAccountError(String message) {
        super(message);
    }
}
