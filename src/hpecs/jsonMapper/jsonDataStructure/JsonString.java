package hpecs.jsonMapper.jsonDataStructure;

import hpecs.queryMapper.queryDataStructure.Queryable;
import hpecs.queryMapper.queryExceptions.ErrorNotArrayException;
import hpecs.queryMapper.queryExceptions.ErrorNotObjectException;

public class JsonString implements IJsonValue, Queryable {

    private String type;
    private String value;

    public JsonString(String value){
        this.type = IJsonValue.JSON_TYPE_LIST[2];
        this.value = value;
    }

    // IJSONVALUE METHODS:
    @Override
    public String getType() {
        return this.type;
    }
    @Override
    public Object getValue() {
        return value;
    }
    @Override
    public void put(IJsonValue iJsonValue) {
        value = iJsonValue.getValue().toString();
    }
    @Override
    public Integer size() {
        return value.length();
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
        return value;
    }

}
