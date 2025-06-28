package skypay.test.exception;

public class UserExistenceException extends Exception{
    public UserExistenceException(String message) {
        super(message);
    }
}
