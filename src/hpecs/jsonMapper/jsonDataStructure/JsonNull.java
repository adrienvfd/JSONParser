package hpecs.jsonMapper.jsonDataStructure;


import hpecs.queryMapper.queryDataStructure.Queryable;
import hpecs.queryMapper.queryExceptions.ErrorNotArrayException;
import hpecs.queryMapper.queryExceptions.ErrorNotObjectException;

public class JsonNull implements IJsonValue, Queryable {

    private String type;

    JsonNull(){
        this.type = IJsonValue.JSON_TYPE_LIST[5];
    }

    // IJSONVALUE METHODS
    @Override
    public String getType() {
        return this.type;
    }
    @Override
    public void put(IJsonValue iJsonValue) {
    }
    @Override
    public Object getValue() {
        return null;
    }
    @Override
    public Integer size() {
        return null;
    }

    // QUERYABLE METHODS:
    @Override
    public Queryable get(Integer i) throws ErrorNotArrayException {
        throw new ErrorNotArrayException();
    }
    @Override
    public Queryable get(String key) throws ErrorNotObjectException {
        throw new ErrorNotObjectException();
    }
    @Override
    public void put(String key, String value) throws ErrorNotObjectException {
        throw new ErrorNotObjectException();
    }
    @Override
    public void del(String key) throws ErrorNotObjectException{
        throw new ErrorNotObjectException();
    }
    @Override
    public void del(Integer index) {
        throw new ErrorNotArrayException();
    }

    //TOSTRING OVERRIDE:
    @Override
    public String toString() {
        return "null";
    }

}
