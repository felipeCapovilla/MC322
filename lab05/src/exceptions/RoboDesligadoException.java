package exceptions;

public class RoboDesligadoException extends RuntimeException {
    
    public RoboDesligadoException(){
        super();
    }

    public RoboDesligadoException(String errorMessage){
        super(errorMessage);
    }

    public RoboDesligadoException(Throwable error){
        super(error);
    }

    public RoboDesligadoException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
