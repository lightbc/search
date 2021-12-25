package exceptions;

/**
 * 无效时间异常
 */
public class InvalidTimeException extends Exception {

    public InvalidTimeException(){

    }

    public InvalidTimeException(String message){
        super(message);
    }
}
