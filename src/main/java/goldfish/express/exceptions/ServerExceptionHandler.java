package goldfish.express.exceptions;

import goldfish.express.domain.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleExceptions(Exception ex , WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage , HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = UserServiceException.class)
    public ResponseEntity<ErrorMessage> handleUserServiceException(UserServiceException ex, WebRequest req){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
