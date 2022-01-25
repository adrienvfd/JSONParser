package hpecs.queryMapper.queryDataStructure;

import hpecs.queryMapper.queryExceptions.ErrorNotArrayException;
import hpecs.queryMapper.queryExceptions.ErrorNotObjectException;
import hpecs.queryMapper.queryExceptions.KeyNotFoundException;

public interface Queryable {
    Queryable get(Integer i) throws ErrorNotArrayException;
    Queryable get(String key) throws ErrorNotObjectException, KeyNotFoundException;
    void put(String key, String value);
    void del(String key);
    void del(Integer index);
}
