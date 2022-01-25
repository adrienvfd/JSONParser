package hpecs.queryMapper.queryExceptions;

public class ErrorIndexOutOfRangeException extends RuntimeException {

    private static final String ERROR_MESSAGE = "ERROR_INDEX_OUT_OF_RANGE";

    public ErrorIndexOutOfRangeException(){
        super(ERROR_MESSAGE);
    }

    @Override
    public String toString(){
        return ERROR_MESSAGE;
    }
}
