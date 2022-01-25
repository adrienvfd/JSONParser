package hpecs.queryMapper.queryExceptions;

public class KeyNotFoundException extends RuntimeException{

    private static final String ERROR_MESSAGE = "KEY_NOT_FOUND";

    public KeyNotFoundException(){
        super(ERROR_MESSAGE);
    }

    @Override
    public String toString(){
        return ERROR_MESSAGE;
    }
}
