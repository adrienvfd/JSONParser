package hpecs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class EventWriter {

    String outputPath;
    LinkedList<String> events;

    EventWriter(LinkedList<String> events, String path){
        this.outputPath = path;
        this.events = events;
    }

    void execute() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath))){
            while (!events.isEmpty()) {
                String event = events.removeFirst();
                bufferedWriter.write(event);
                bufferedWriter.newLine();
            }
        }
    }
}
