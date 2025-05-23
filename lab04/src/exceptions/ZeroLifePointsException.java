package exceptions;

public class ZeroLifePointsException extends RuntimeException{

    public ZeroLifePointsException(){
        super();
    }

    public ZeroLifePointsException(String errorMessage){
        super(errorMessage);
    }

    public ZeroLifePointsException(Throwable error){
        super(error);
    }

    public ZeroLifePointsException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
