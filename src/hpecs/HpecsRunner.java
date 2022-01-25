package hpecs;

import hpecs.helpers.ProjectPaths;
import hpecs.jsonMapper.JsonMapper;
import hpecs.queryMapper.QueryMapper;
import hpecs.textFileComparator.TextFileComparator;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class HpecsRunner {

    public static void main(String[] args) throws IOException {

        int fileIndex = 5;
        String jsonFileName = "traffic_" + fileIndex + ".json";
        String queryFileName = "query_" + fileIndex + ".txt";
        String outputFile = "results_" + fileIndex + ".txt";
        String referenceFile = "ref_" + fileIndex + ".txt";

        //USER-PROMPT:
        /*
        Scanner sc = new Scanner(System.in);
        String[] promptUser = promptUser(sc);
        sc.close();
        jsonFileName = promptUser[0];
        queryFileName = promptUser[1];
        outputFile = promptUser[2];
         */

        //1. Read the input files & load them in RAM w/ a data structure:
        JsonMapper myJSONMapper = new JsonMapper(ProjectPaths.JSON_PATH + jsonFileName);
        QueryMapper myQueryMapper = new QueryMapper(ProjectPaths.QUERY_PATH + queryFileName);
        System.out.println("Mapping the json file...");
        myJSONMapper.execute();
        System.out.println("Mapping the query file...");
        myQueryMapper.execute();
        System.out.println();

        //2. Simulate the queries on the data structure & get the output events:
        EventListBuilder eventListBuilder = new EventListBuilder(myJSONMapper, myQueryMapper);
        System.out.println("Simulating the queries on the json...");
        eventListBuilder.execute();
        System.out.println("Retrieving the events...");
        LinkedList<String> events = eventListBuilder.execute().getEvents();

        //3. Save a file of the output events:
       EventWriter eventWriter = new EventWriter(events, ProjectPaths.OUTPUT_PATH + outputFile);
        System.out.println("Saving the events...");
        eventWriter.execute();

        //4. Compare the events with reference output:
        TextFileComparator textFileComparator = new TextFileComparator(ProjectPaths.REFERENCE_PATH + referenceFile, ProjectPaths.OUTPUT_PATH + outputFile);
        System.out.println("Comparing the files...");
        textFileComparator.execute(); // Will check & print a summary if the 2 files are identical or not.
    }

    public static String[] promptUser(Scanner sc) {
        System.out.println("Type a name of a JSON file (ex: traffic_1.json)");
        String jsonFileName = sc.nextLine();
        System.out.println("Type the name of a query file (ex query_1.txt)");
        String queryFileName = sc.nextLine();
        System.out.println("Type the name of the output file (ex results_1.txt)");
        String outputFile = sc.nextLine();
        System.out.println("Press enter to run the program..");
        sc.nextLine();
        return new String[]{jsonFileName, queryFileName, outputFile};
    }
}
