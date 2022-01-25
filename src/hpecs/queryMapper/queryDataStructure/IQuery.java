package hpecs.queryMapper.queryDataStructure;


public interface IQuery {

    String[] QUERY_TYPES = {"GET", "PUT", "DEL"};

    String getType();

    String execute(Queryable data);

    default String generateErrorMessage(String queryType, String queryCode, String queryKey){
        return (queryType + "_" + queryCode + " " + queryKey);
    }
}
