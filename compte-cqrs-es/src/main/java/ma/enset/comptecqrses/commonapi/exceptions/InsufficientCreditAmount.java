package ma.enset.comptecqrses.commonapi.exceptions;


public class InsufficientCreditAmount extends RuntimeException {
    public InsufficientCreditAmount(String message) {
        super(message);
    }
}