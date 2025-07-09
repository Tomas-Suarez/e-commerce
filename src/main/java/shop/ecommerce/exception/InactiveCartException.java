package shop.ecommerce.exception;

public class InactiveCartException extends RuntimeException {
    public InactiveCartException(String message) {
        super(message);
    }
}
