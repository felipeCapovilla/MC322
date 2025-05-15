package exceptions;

public class PointOutOfMapException extends RuntimeException {

    public PointOutOfMapException(){
        super();
    }

    public PointOutOfMapException(String errorMessage){
        super(errorMessage);
    }

    public PointOutOfMapException(Throwable error){
        super(error);
    }

    public PointOutOfMapException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
