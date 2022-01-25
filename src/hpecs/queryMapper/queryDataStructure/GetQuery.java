package hpecs.queryMapper.queryDataStructure;

import hpecs.queryMapper.queryExceptions.ErrorIndexOutOfRangeException;
import hpecs.queryMapper.queryExceptions.ErrorNotArrayException;
import hpecs.queryMapper.queryExceptions.ErrorNotObjectException;
import hpecs.queryMapper.queryExceptions.KeyNotFoundException;

import java.util.LinkedList;

public class GetQuery implements IQuery {

    private static final String type = IQuery.QUERY_TYPES[0];

    private String key;                 // the key we are looking for
    private LinkedList<String> keys;    // the "path" that leads to the key

    public GetQuery(LinkedList<String> keys) {
        this.key = keys.removeLast();
        this.keys = keys;
    }

    // IQUERY METHODS:
    @Override
    public String getType() {
        return type;
    }

    @Override
    public String execute(Queryable root) {
        String crtKey = "";             // Can be Integer (ex: 2) or String with it's "" (ex "key")
        String crtStringKey = "root";   // Value of the last key of type String (ex "Key" (yes) vs 1 (no))

        Queryable data = root;

        // The goal of this while-loop is to find the location where to get the "key" from:
        while (!keys.isEmpty()) {
            crtKey = keys.removeFirst();
            try {
                // If the key is a Number, look in JsonArray:
                if (Character.isDigit(crtKey.charAt(0))) {
                    // Look for index in Queryable of type JsonArray:
                    data = data.get(Integer.parseInt(crtKey));
                } else if (crtKey.charAt(0) == '\"') {
                    // Look for key in Queryable of class JsonArray:
                    data = data.get(crtKey);
                    crtStringKey = crtKey;
                }
            } catch (ErrorNotArrayException e) {
                return generateErrorMessage(IQuery.QUERY_TYPES[0], e.toString(), crtStringKey);
            } catch (ErrorNotObjectException e) {
                return generateErrorMessage(IQuery.QUERY_TYPES[0], e.toString(), crtStringKey);
            } catch (KeyNotFoundException e) {
                crtStringKey = crtKey;
                return generateErrorMessage(IQuery.QUERY_TYPES[0], e.toString(), crtStringKey);
            } catch (ErrorIndexOutOfRangeException e) {
                return generateErrorMessage(IQuery.QUERY_TYPES[0], e.toString(), crtStringKey);
            }
        }

        // Once we found the place, get the key from this place (object);
        try {
            // If the key is the index of an array:
            if (Character.isDigit(key.charAt(0))) return data.get(Integer.parseInt(key)).toString();
            // If the key is a string-key of an object:
            return data.get(key).toString();

        } catch (ErrorNotArrayException e) {
            return generateErrorMessage(IQuery.QUERY_TYPES[0], e.toString(), crtStringKey);
        } catch (ErrorNotObjectException e) {
            return generateErrorMessage(IQuery.QUERY_TYPES[0], e.toString(), crtStringKey);
        } catch (KeyNotFoundException e) {
            return generateErrorMessage(IQuery.QUERY_TYPES[0], e.toString(), key);
        } catch (ErrorIndexOutOfRangeException e) {
            return generateErrorMessage(IQuery.QUERY_TYPES[0], e.toString(), crtStringKey);
        }
    }
}
