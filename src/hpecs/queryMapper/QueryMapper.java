package hpecs.queryMapper;

import hpecs.queryMapper.queryDataStructure.IQuery;
import hpecs.queryMapper.queryDataStructure.QueryFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class QueryMapper {

    private String inputName;
    private LinkedList<IQuery> queryQueue;

    public QueryMapper(String inputName) throws IOException {
        this.inputName = inputName;
    }

    public QueryMapper execute() throws IOException {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(inputName)))) {
            queryQueue = new LinkedList<>();

            while (sc.hasNextLine()) {
                LinkedList<String> queryLine = new LinkedList<>();
                String nextLine = sc.nextLine();

                // Get the type:
                queryLine.addLast(nextLine.substring(0, 3));

                // Get the query list:
                for (int i = 3; i < nextLine.length(); i++) {
                    // Read Current Char:
                    char crtChar = nextLine.charAt(i);

                    //If string, read it:
                    if (crtChar == '\"') {
                        StringBuilder stb = new StringBuilder();
                        do {
                            stb.append(crtChar);
                            i++;
                            crtChar = nextLine.charAt(i);
                        } while (crtChar != '\"');
                        stb.append(crtChar);
                        queryLine.addLast(stb.toString());

                        //If number, read it:
                    } else if (Character.isDigit(crtChar)) {
                        StringBuilder stb = new StringBuilder();
                        do {
                            stb.append(crtChar);
                            i++;
                            if (i == nextLine.length()) break;
                            crtChar = nextLine.charAt(i);
                        } while (Character.isDigit(crtChar));
                        i--;
                        queryLine.addLast(stb.toString());
                    //If it's a boolean
                    } else if (crtChar == 't' || crtChar == 'f'){
                        if (crtChar == 't'){
                            i+= "true".length() - 1;
                            queryLine.addLast("true");
                        } else {
                            i+= "false".length() - 1;
                            queryLine.addLast("false");
                        }
                    }
                    else {
                        continue;
                    }
                }

                // Make a query object
                QueryFactory newQueryFactory = new QueryFactory(queryLine);
                IQuery newQuery = newQueryFactory.makeQuery();
                // Put it in the QueryQueue
                queryQueue.addLast(newQuery);
            }
        }
        return this;
    }

    public LinkedList<IQuery> getQueryQueue(){
        return queryQueue;
    }
}
