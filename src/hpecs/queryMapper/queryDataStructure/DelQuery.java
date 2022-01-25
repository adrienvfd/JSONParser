package hpecs.queryMapper.queryDataStructure;

import hpecs.queryMapper.queryExceptions.*;

import java.util.LinkedList;

public class DelQuery implements IQuery {

    private static final String type = IQuery.QUERY_TYPES[2];
    private LinkedList<String> keys;
    private String key;

    DelQuery(LinkedList<String> keys){
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
        Queryable data = root;
        String crtStringKey = "root";

        while (true){
            String crtKey = keys.removeFirst();

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
                if (keys.size() == 0) break;
            } catch (ErrorNotArrayException e){
                return generateErrorMessage(IQuery.QUERY_TYPES[2], e.toString(), crtStringKey);
            } catch (ErrorNotObjectException e){
                return generateErrorMessage(IQuery.QUERY_TYPES[2], e.toString(), crtStringKey);
            } catch (KeyNotFoundException e){
                crtStringKey = crtKey;
                return generateErrorMessage(IQuery.QUERY_TYPES[2], e.toString(), crtStringKey);
            } catch (ErrorIndexOutOfRangeException e){
                return generateErrorMessage(IQuery.QUERY_TYPES[2], e.toString(), crtStringKey);
            }

        }
        // The key is deleted:
        try {
            if (Character.isDigit(key.charAt(0))) {
                Integer index = Integer.parseInt(key);
                data.del(index);
            } else {
                data.del(key);
            }
        } catch (ErrorNotArrayException e){
            return generateErrorMessage(IQuery.QUERY_TYPES[2], e.toString(), crtStringKey);
        } catch (ErrorNotObjectException e){
            return generateErrorMessage(IQuery.QUERY_TYPES[2], e.toString(), crtStringKey);
        } catch (KeyNotFoundException e){
            return generateErrorMessage(IQuery.QUERY_TYPES[2], e.toString(), crtStringKey);
        } catch (ErrorIndexOutOfRangeException e){
            return generateErrorMessage(IQuery.QUERY_TYPES[2], e.toString(), crtStringKey);
        }
            return null;
    }
}
