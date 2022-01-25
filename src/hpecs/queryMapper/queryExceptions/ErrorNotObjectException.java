package hpecs.queryMapper.queryExceptions;

public class ErrorNotObjectException extends RuntimeException{

    private static final String ERROR_MESSAGE = "ERROR_NOT_OBJECT";

    public ErrorNotObjectException(){
        super(ERROR_MESSAGE);
    }

    @Override
    public String toString(){
        return ERROR_MESSAGE;
    }
}
