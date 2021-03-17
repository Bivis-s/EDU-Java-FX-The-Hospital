package errors;

public class AccountAlreadyExistsError extends Error {
    public AccountAlreadyExistsError(String message) {
        super(message);
    }
}
