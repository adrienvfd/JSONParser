package hpecs.jsonMapper.jsonDataStructure.jsonStructures.jsonContainers;

import hpecs.jsonMapper.jsonDataStructure.IJsonValue;
import hpecs.jsonMapper.jsonDataStructure.JsonValueFactory;
import hpecs.queryMapper.queryDataStructure.Queryable;
import hpecs.queryMapper.queryExceptions.ErrorIndexOutOfRangeException;
import hpecs.queryMapper.queryExceptions.ErrorNotArrayException;
import hpecs.queryMapper.queryExceptions.ErrorNotObjectException;
import hpecs.queryMapper.queryExceptions.KeyNotFoundException;

import java.util.HashMap;


public class JsonObject implements IJsonValue, Queryable {

    private String type;
    private HashMap<String, IJsonValue> value;

    public JsonObject(){
        this.value = new HashMap<>();

    }

    // ADD TO OBJECT METHOD:
    public void put(String key, IJsonValue value) {
        this.type = IJsonValue.JSON_TYPE_LIST[1];
        this.value.put(key, value);
    }

    // IJSONVALUE METHODS:
    @Override
    public String getType() {
        return this.type;
    }
    @Override
    public void put(IJsonValue iJsonValue) {
        //System.out.println(iJsonValue.getType() + " has been added weiirdly with value " + iJsonValue.getValue());
        if (iJsonValue.getType() != this.type) throw new ErrorNotObjectException();
        this.value = (HashMap<String, IJsonValue>) iJsonValue.getValue();
    }
    @Override
    public Integer size() {
        return value.size();
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
    public Queryable get(String key) throws KeyNotFoundException {
        if (!(value.containsKey(key))) {
            throw new KeyNotFoundException();
        }
        return (Queryable) value.get(key);
    }
    @Override
    public void put(String key, String value) {
        String type = IJsonValue.getType(value);
        JsonValueFactory newJsonValueFactory = new JsonValueFactory(type, value);
        IJsonValue jsonValueToAdd = newJsonValueFactory.makeJsonValue();
        this.value.put(key, jsonValueToAdd);
    }
    @Override
    public void del(String key){
        value.remove(key);
    }
    @Override
    public void del(Integer index) {
        throw new ErrorNotArrayException();
    }

    //TOSTRING OVERRIDE:
    @Override
    public String toString() {
        return IJsonValue.JSON_TYPE_LIST[1];
    }




}