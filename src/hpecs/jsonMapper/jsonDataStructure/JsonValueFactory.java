package hpecs.jsonMapper.jsonDataStructure;

import hpecs.jsonMapper.jsonDataStructure.jsonStructures.jsonContainers.JsonArray;
import hpecs.jsonMapper.jsonDataStructure.jsonStructures.jsonContainers.JsonObject;

import java.util.Arrays;

public class JsonValueFactory {

    private String type;
    private String value;

    public  JsonValueFactory (String type){
        this(type, "");
    }
    public JsonValueFactory(String type, String value) {
        this.type = type.toUpperCase();
        this.value = value;
    }

    public IJsonValue makeJsonValue(){
        switch (Arrays.stream(IJsonValue.JSON_TYPE_LIST).toList().indexOf(type)) {
            case 0: return new JsonArray();
            case 1: return new JsonObject();
            case 2: return new JsonString(value);
            case 3: return new JsonNumber(value);
            case 4: return new JsonBool(value);
            case 5: return new JsonNull();
            default:
                System.out.println("Error, this type of json entity does not exist. \n" +
                        "Can't build " + type);
                return null;
        }
    }
}
