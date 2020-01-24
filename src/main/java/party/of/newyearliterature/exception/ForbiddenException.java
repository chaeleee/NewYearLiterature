package party.of.newyearliterature.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UnAuthorizedException
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException{

    private static final long serialVersionUID = -7229854594755871773L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String msg){
        super(msg);
    }
}