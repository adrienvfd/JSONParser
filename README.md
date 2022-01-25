# Json-Parser, final project at Devmind.

High Performance Event Correlation System

This project is a basic JSON Parser.

It takes as input a JSON file (src/hpecs_public_tests/traffic),
and a corresponding query text file (src/hpecs_public_tests/input).

The class JsonMapper parses the json file & loads it in ram using a data structure. \n
The class QueryMapper parses the query file & loads it in ram using a data structure.
The class EventListBuilder simulates the queries on the json & retrieves the events.
The class EventListWriter will write a file with the events & save it (src/hpecs_public_tests/input)
Finally, the class TextFileComparator will compare the output with the expected output file (src/hpecs_public_tests/ref)

The Runner is found in the class HpecsRunner.

Hypothesis for the project:
The JSON & Query files are valid
