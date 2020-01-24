package party.of.newyearliterature.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UnAuthorizedException
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException{

    private static final long serialVersionUID = 2254554765825403922L;

    public UnAuthorizedException() {
        super();
    }

    public UnAuthorizedException(String msg){
        super(msg);
    }
}