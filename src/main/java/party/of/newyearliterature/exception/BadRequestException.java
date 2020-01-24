package party.of.newyearliterature.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadRequestException
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    private static final long serialVersionUID = -532460512028005536L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String msg){
        super(msg);
    }
    
}