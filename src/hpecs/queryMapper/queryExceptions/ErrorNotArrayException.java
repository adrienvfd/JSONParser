package hpecs.queryMapper.queryExceptions;

public class ErrorNotArrayException extends RuntimeException {

    private static final String ERROR_MESSAGE = "ERROR_NOT_ARRAY";

    public ErrorNotArrayException(){
        super(ERROR_MESSAGE);
    }

    @Override
    public String toString(){
        return ERROR_MESSAGE;
    }
}
