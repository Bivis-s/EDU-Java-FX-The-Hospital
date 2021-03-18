package errors;

public class SelfRecordError extends Error {
    public SelfRecordError(String message) {
        super(message);
    }
}
