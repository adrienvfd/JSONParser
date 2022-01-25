package hpecs.jsonMapper.jsonDataStructure.jsonStructures.jsonContainers;

import hpecs.jsonMapper.jsonDataStructure.IJsonValue;
import hpecs.queryMapper.queryDataStructure.Queryable;
import hpecs.queryMapper.queryExceptions.ErrorIndexOutOfRangeException;
import hpecs.queryMapper.queryExceptions.ErrorNotArrayException;
import hpecs.queryMapper.queryExceptions.ErrorNotObjectException;

import java.util.LinkedList;
import java.util.List;

public class JsonArray implements IJsonValue, Queryable {

    private String type;
    List<IJsonValue> value;

    public JsonArray(){
        this.type = IJsonValue.JSON_TYPE_LIST[0];
        this.value = new LinkedList<>();
    }

    // IJSONVALUE METHODS:
    @Override
    public void put(IJsonValue iJsonValue) {
        value.add(iJsonValue);
    }
    @Override
    public String getType() {
        return this.type;
    }
    @Override
    public Integer size(){
        return  value.size();
    }
    @Override
    public Object getValue() {
        return value;
    }

    //QUERYABLE METHODS:
    @Override
    public Queryable get(Integer i) throws ErrorIndexOutOfRangeException {
        if (i < 0 || i >= value.size()) throw new ErrorIndexOutOfRangeException();
        return  (Queryable) this.value.get(i);
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
    public void del(String key) throws ErrorNotObjectException {
        throw new ErrorNotObjectException();
    }
    @Override
    public void del(Integer index) throws ErrorIndexOutOfRangeException{
        if (index > value.size() - 1 || index < 0) throw new ErrorIndexOutOfRangeException();
        value.remove((int) index);
    }

    //TOSTRING OVERRIDE:
    @Override
    public String toString() {
        return IJsonValue.JSON_TYPE_LIST[0];
    }

}