package hpecs.jsonMapper;

import hpecs.jsonMapper.jsonDataStructure.IJsonValue;
import hpecs.jsonMapper.jsonDataStructure.JsonValueFactory;
import hpecs.jsonMapper.jsonDataStructure.jsonStructures.jsonContainers.JsonArray;
import hpecs.jsonMapper.jsonDataStructure.jsonStructures.jsonContainers.JsonObject;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.LinkedList;
import java.util.Scanner;

public class JsonMapper {

    private String inputPath;                       // Input Path of Json file.
    private IJsonValue root;                        // "root" Json object of the json file.
    private LinkedList<IJsonValue> jsonValuesStack; // Stack of the input Json file:
    private LinkedList<String> stringQueue;         // Saves "key" string when waiting for "value" string.

    public JsonMapper(String inputPath) {
        this.stringQueue = new LinkedList<>();
        this.inputPath = inputPath;
    }

    public IJsonValue getRoot(){
        return this.root;
    }

    public JsonMapper execute() throws IOException {

        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(inputPath)))){
            //Initialize MappedFile
            initializeMappedFile(sc);
            while(sc.hasNextLine()){

                String nextLine = sc.nextLine();

                for (int i = 0; i < nextLine.length(); i++){

                    char crtChar = nextLine.charAt(i);
                    // Skip all these characters:
                    if(crtChar == ' ' || crtChar == ':' || crtChar == ',') continue;

                    //------ JSON_ARRAYS -------
                    if(crtChar == '[') {
                        // New JSonArray :)
                        JsonValueFactory jsonArrFactory = new JsonValueFactory(IJsonValue.JSON_TYPE_LIST[0]);
                        IJsonValue newJSonArray = jsonArrFactory.makeJsonValue();
                        // Try to add pair (String:Arr) to last location in the stack:
                        if(!tryAddPairToStack(newJSonArray)){
                            jsonValuesStack.getLast().put(newJSonArray);
                        }
                        // In either case, add JSonArray to the stack:
                        jsonValuesStack.addLast(newJSonArray);

                    } else if(crtChar == ']') {
                        // Delete the last item of the stack
                        jsonValuesStack.removeLast();

                        // ------ JSON_OBJECTS --------
                    } else if(crtChar == '{'){
                        //New JSonObject :)
                        JsonValueFactory jsonObjFactory = new JsonValueFactory(IJsonValue.JSON_TYPE_LIST[1]);
                        IJsonValue newJsonObj = jsonObjFactory.makeJsonValue();
                        //Try add a pair (String: obj) to potential last object in stack:
                        if (!tryAddPairToStack(newJsonObj)) {
                            // If it fails, add the current obj to current location:
                            //Add newJsonObj @ the current location:
                            jsonValuesStack.getLast().put(newJsonObj);
                        }
                        // Add this Obj to the stack:
                        jsonValuesStack.addLast(newJsonObj);
                    } else if(crtChar == '}'){
                        // Delete the last item of the stack
                        jsonValuesStack.removeLast();

                        //-------- STRINGS -------------
                    } else if(crtChar == '"'){
                        // Read a new string entirely:
                        StringBuilder strB = new StringBuilder();
                        // iterate through the line until "" reached;
                        do {
                            strB.append(crtChar);
                            i++;
                            crtChar = nextLine.charAt(i);
                        } while (!(crtChar == '"' ));
                        strB.append(crtChar);                    // append last ":
                        String newString = strB.toString();

                        // Try to add a pair in the crt stack:
                        JsonValueFactory newStringJsonValueFactory = new JsonValueFactory(IJsonValue.JSON_TYPE_LIST[2], newString);
                        IJsonValue newStringJsonValue = newStringJsonValueFactory.makeJsonValue();
                        if (!tryAddPairToStack(newStringJsonValue)){
                            // if tryAddPairToStack not successful, then:
                            this.stringQueue.addLast(newString);
                        }
                        // ------ NUMBERS ---------
                    } else if(Character.isDigit(crtChar)){
                        StringBuilder stB = new StringBuilder();
                        do{
                            stB.append(crtChar);
                            i++;
                            crtChar = nextLine.charAt(i);
                        } while(Character.isDigit(crtChar));
                        i--;
                        // Add the number with its key. If key not found... there is a problem :).
                        JsonValueFactory newNumberJsonFactory = new JsonValueFactory(IJsonValue.JSON_TYPE_LIST[3], stB.toString());
                        IJsonValue newNumber = newNumberJsonFactory.makeJsonValue();
                        if(!tryAddPairToStack(newNumber)) throw new IllegalStateException("JSON_NUMBER not associated with any key");

                        // ------ BOOL ---------
                    } else if (crtChar == 't' || crtChar == 'f') {
                        StringBuilder strB = new StringBuilder();
                        int length = crtChar == 't' ? "true".length() : "false".length();
                        for (int j = 0; j < length; j++) {
                            strB.append(crtChar);
                            i++;
                            crtChar = nextLine.charAt(i);
                        }
                        String newString = strB.toString();
                        JsonValueFactory newBoolJsonFactory = new JsonValueFactory(IJsonValue.JSON_TYPE_LIST[4], newString);
                        IJsonValue newBool = newBoolJsonFactory.makeJsonValue();
                        if (!tryAddPairToStack(newBool)) throw new IllegalStateException("JSON_BOOL not associated with any key");

                        // ------ NULL ---------
                    } else if (crtChar == 'n'){
                        StringBuilder strB = new StringBuilder();
                        for(int j = 0; j < "null".length(); i++){
                            strB.append(crtChar);
                        }
                        if (!strB.toString().equals("null")) throw new IllegalStateException("Tried to read a null, but string does not match");
                        // Add the null with its key.
                        JsonValueFactory newNullFactory = new JsonValueFactory(IJsonValue.JSON_TYPE_LIST[5]);
                        IJsonValue newNull= newNullFactory.makeJsonValue();
                        if(!tryAddPairToStack(newNull)) throw new IllegalStateException("JSON_NULL not associated with any key");
                    }
                }
            }
        }
        return this;
    }

    private void initializeMappedFile(Scanner sc){
        // Initialize MappedFile With a first array:
        if (sc.hasNextLine()){
            String nextLine = sc.nextLine();
            if(nextLine.contains("[")){
                this.root = new JsonArray();
            }
        }
        // Initialize the stack by putting the root in it
        jsonValuesStack = new LinkedList<>();
        jsonValuesStack.addLast(root);
    }
    private boolean tryAddPairToStack(IJsonValue jsonValue) throws InvalidObjectException {
        // If the stringQueue is empty: the newString is a key:
        if (stringQueue.size() == 1) {
            // There is a key in the Queue!
            String key = stringQueue.removeFirst();
            // Last item in the stack must be an Object in order to add a pair...
            IJsonValue lastInStack = jsonValuesStack.getLast();
            if (!(lastInStack instanceof JsonObject)) {
                throw new InvalidObjectException("Can add a pair ONLY in an object");
            }
            JsonObject lastObj = (JsonObject) (jsonValuesStack.getLast());
            // Add pair to object:
            lastObj.put(key, jsonValue);
            return true;
        }
        return false;
    }
}
