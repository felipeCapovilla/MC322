package exceptions;

public class ColisaoException extends RuntimeException{

    public ColisaoException(){
        super();
    }

    public ColisaoException(String errorMessage){
        super(errorMessage);
    }

    public ColisaoException(Throwable error){
        super(error);
    }

    public ColisaoException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
