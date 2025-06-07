package exceptions;

public class NoModuleException extends RuntimeException{

    public NoModuleException(){
        super();
    }

    public NoModuleException(String errorMessage){
        super(errorMessage);
    }

    public NoModuleException(Throwable error){
        super(error);
    }

    public NoModuleException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}

