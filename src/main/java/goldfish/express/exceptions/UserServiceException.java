package goldfish.express.exceptions;

public class UserServiceException extends RuntimeException{
    private static final long serialVersionUID = -7649229586633992143L;

    public UserServiceException(String message){
        super(message);
    }
}
