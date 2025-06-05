package exceptions;

public class SensorMissingException extends RuntimeException{

    public SensorMissingException(){
        super();
    }

    public SensorMissingException(String errorMessage){
        super(errorMessage);
    }

    public SensorMissingException(Throwable error){
        super(error);
    }

    public SensorMissingException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
