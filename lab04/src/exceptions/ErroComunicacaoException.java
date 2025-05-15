package exceptions;

public class ErroComunicacaoException extends RuntimeException{

    public ErroComunicacaoException(){
        super();
    }

    public ErroComunicacaoException(String errorMessage){
        super(errorMessage);
    }

    public ErroComunicacaoException(Throwable error){
        super(error);
    }

    public ErroComunicacaoException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
