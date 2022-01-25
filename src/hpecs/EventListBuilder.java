package hpecs;

import hpecs.jsonMapper.JsonMapper;
import hpecs.jsonMapper.jsonDataStructure.IJsonValue;
import hpecs.queryMapper.queryDataStructure.Queryable;
import hpecs.queryMapper.QueryMapper;
import hpecs.queryMapper.queryDataStructure.IQuery;

import java.io.IOException;
import java.util.LinkedList;

public class EventListBuilder {

    private Queryable jsonRoot;
    private LinkedList<IQuery> queryQueue;
    private LinkedList<String> events;

    // In case we want to first create the mappers...
    public EventListBuilder(String jsonTrafficPath, String queryTxtPath) throws IOException {
        this(new JsonMapper(jsonTrafficPath).execute(), new QueryMapper(queryTxtPath).execute());
    }
    public EventListBuilder(JsonMapper jsonMap, QueryMapper queryMap){
        this(jsonMap.getRoot(), queryMap.getQueryQueue());
    }
    public EventListBuilder(IJsonValue jsonRoot, LinkedList<IQuery> queryQueue){
        this.jsonRoot =(Queryable) jsonRoot;
        this.queryQueue = queryQueue;
        this.events = new LinkedList<>();
    }

    public LinkedList<String> getEvents() {
        return this.events;
    }

    public EventListBuilder execute() {
        while (!queryQueue.isEmpty()) {
            IQuery currentQuery = queryQueue.removeFirst();         //  pop a query from the queryQueue
            String event = currentQuery.execute(this.jsonRoot);     //  execute query on json data structure
            if (event != null) events.addLast(event);               //  add event to event queue
        }
        return this;
    }


}
