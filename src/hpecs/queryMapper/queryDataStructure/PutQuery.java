package hpecs.queryMapper.queryDataStructure;

import hpecs.queryMapper.queryExceptions.ErrorIndexOutOfRangeException;
import hpecs.queryMapper.queryExceptions.ErrorNotArrayException;
import hpecs.queryMapper.queryExceptions.ErrorNotObjectException;
import hpecs.queryMapper.queryExceptions.KeyNotFoundException;

import java.util.LinkedList;

class PutQuery implements IQuery {

    private static final String type = QUERY_TYPES[1];

    private String key;
    private String value;
    private LinkedList<String> keys;

    PutQuery(LinkedList<String> keys) {
        this.value = keys.removeLast();
        this.key = keys.removeLast();
        this.keys = keys;
    }

    // IQUERY METHODS:
    @Override
    public String getType() {
        return type;
    }

    @Override
    public String execute(Queryable root){
        Queryable data = root;
        String crtStringKey = "root";

        while (!keys.isEmpty()){
            String crtKey = keys.removeFirst();
            try {
                // If the key is a Number, look in JsonArray:
                if (Character.isDigit(crtKey.charAt(0))) {
                    // Look for index in Queryable of type JsonArray:
                    data = data.get(Integer.parseInt(crtKey));
                    //crtStringKey = crtKey;
                } else if (crtKey.charAt(0) == '\"') {
                    // Look for key in Queryable of class JsonArray:
                    data = data.get(crtKey);
                    crtStringKey = crtKey;
                }
            } catch (ErrorNotArrayException e){
                return generateErrorMessage(IQuery.QUERY_TYPES[1], e.toString(), crtStringKey);
            } catch (ErrorNotObjectException e){
                return generateErrorMessage(IQuery.QUERY_TYPES[1], e.toString(), crtStringKey);
            } catch (KeyNotFoundException e){
                crtStringKey = crtKey;
                return generateErrorMessage(IQuery.QUERY_TYPES[1], e.toString(), crtStringKey);
            } catch (ErrorIndexOutOfRangeException e){
                return generateErrorMessage(IQuery.QUERY_TYPES[1], e.toString(), crtStringKey);
            }
        }
        // the last 2 keys are key : value, they are added :
        try {
            data.put(this.key, this.value);
        } catch (ErrorNotObjectException e){
            return generateErrorMessage(IQuery.QUERY_TYPES[1], e.toString(), crtStringKey);
        }
        return null;
    }
}
