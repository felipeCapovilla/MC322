package exceptions;

public class LowBatteryException extends RuntimeException{

    public LowBatteryException(){
        super();
    }

    public LowBatteryException(String errorMessage){
        super(errorMessage);
    }

    public LowBatteryException(Throwable error){
        super(error);
    }

    public LowBatteryException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
