package party.of.newyearliterature.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFoundException
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NotFoundException(){
        super();
    }

    public NotFoundException(String msg){
        super(msg);
    }
    
}