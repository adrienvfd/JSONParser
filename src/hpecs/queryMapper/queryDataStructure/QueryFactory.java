package hpecs.queryMapper.queryDataStructure;

import java.util.Arrays;
import java.util.LinkedList;

public class QueryFactory {

    private String type;
    private LinkedList<String> keys;
    private String value;

    public QueryFactory(LinkedList<String> queryLine) {
        // Initialize Keys LinkedList
        keys = queryLine;
        // Initialize type;
        type = keys.removeFirst();

    }

    public IQuery makeQuery() {
        //Query Factory:
        switch (Arrays.stream(IQuery.QUERY_TYPES).toList().indexOf(type)) {
            case 0:
                return new GetQuery(keys);
            case 1:
                return new PutQuery(keys);
            case 2:
                return new DelQuery(keys);
            default:
                System.out.println("Error, the type of query " + type + " does not exist");
                return null;
        }
    }
}
