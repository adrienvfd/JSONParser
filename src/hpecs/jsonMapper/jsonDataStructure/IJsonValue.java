package hpecs.jsonMapper.jsonDataStructure;

public interface IJsonValue {

    String[] JSON_TYPE_LIST = {"JSON_ARRAY", "JSON_OBJECT", "JSON_STRING", "JSON_NUMBER", "JSON_BOOL", "JSON_NULL"};

    void put(IJsonValue iJsonValue);
    Integer size();
    Object getValue();
    String getType();

    static String getType(String value){
        if (value.charAt(0) == '[') return JSON_TYPE_LIST[0];
        if (value.charAt(0) == '{') return JSON_TYPE_LIST[1];
        if (value.charAt(0) == '\"') return JSON_TYPE_LIST[2];
        if (Character.isDigit(value.charAt(0))) return JSON_TYPE_LIST[3];
        if (value.equals("true") || value.equals("false")) return JSON_TYPE_LIST[4];
        if (value.equals("null")) return JSON_TYPE_LIST[5];
        System.err.println("Error, unkwnown type for value : " + value);
        return "UNKNOWN TYPE";
    }

}
