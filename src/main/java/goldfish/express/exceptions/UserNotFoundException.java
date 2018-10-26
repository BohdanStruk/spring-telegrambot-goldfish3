package goldfish.express.exceptions;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -4158778395219466658L;

    public UserNotFoundException(String message){
        super(message);
    }
}
