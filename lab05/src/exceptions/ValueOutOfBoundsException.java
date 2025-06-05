package exceptions;

public class ValueOutOfBoundsException extends RuntimeException{

    public ValueOutOfBoundsException(){
        super();
    }

    public ValueOutOfBoundsException(String errorMessage){
        super(errorMessage);
    }

    public ValueOutOfBoundsException(Throwable error){
        super(error);
    }

    public ValueOutOfBoundsException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
