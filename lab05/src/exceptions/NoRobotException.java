package exceptions;

public class NoRobotException extends RuntimeException{

    public NoRobotException(){
        super();
    }

    public NoRobotException(String errorMessage){
        super(errorMessage);
    }

    public NoRobotException(Throwable error){
        super(error);
    }

    public NoRobotException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}

