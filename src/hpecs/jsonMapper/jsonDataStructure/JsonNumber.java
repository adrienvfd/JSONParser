package hpecs.jsonMapper.jsonDataStructure;

import hpecs.queryMapper.queryDataStructure.Queryable;
import hpecs.queryMapper.queryExceptions.ErrorNotArrayException;
import hpecs.queryMapper.queryExceptions.ErrorNotObjectException;
import hpecs.queryMapper.queryExceptions.KeyNotFoundException;

public class JsonNumber implements IJsonValue, Queryable {

    private String type;
    private String value;

    JsonNumber(String value){
        this.type = IJsonValue.JSON_TYPE_LIST[3];
        this.value = value;
    }

    // IJSONVALUE METHODS:
    @Override
    public String getType() {
        return type;
    }
    @Override
    public void put(IJsonValue iJsonValue) {
        this.value = (String) iJsonValue.getValue();
    }
    @Override
    public Integer size() {
        if (value == null) return 0;
        return 1;
    }
    @Override
    public Object getValue() {
        return value;
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

    //TOSTRING OVERRIDE
    @Override
    public String toString() {
        return value;
    }


}
