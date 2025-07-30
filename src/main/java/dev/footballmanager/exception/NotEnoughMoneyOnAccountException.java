package dev.footballmanager.exception;

public class NotEnoughMoneyOnAccountException extends RuntimeException {
    public NotEnoughMoneyOnAccountException(String message) {
        super(message);
    }
}
