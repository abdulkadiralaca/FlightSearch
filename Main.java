import java.io.File;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException {

        ArrayList<Flight> flightsArray = new ArrayList<Flight>();    //I created an array to store all details of each flights

        try {
            System.setOut(new PrintStream(new File("output.txt")));    // I can write anything I want to output.txt with using
        } catch (Exception e) {                                              //only system.out.println() thanks to this code part
            e.printStackTrace();
        }

        FileOperations fileOperations = new FileOperations();      // objects of my classes
        CommandOp commandOp = new CommandOp();

        fileOperations.readingFlightsFile(args[1],flightsArray);      //reading flightsList.txt
        fileOperations.readingAirportsFiles(args[0], flightsArray, commandOp.airportList);    //reading airportList.txt
        commandOp.CreateGraph(flightsArray);                                            //creating my graph with using flightsArray
        fileOperations.readCommandFile(args[2], commandOp);           //reading command file and writes output file
    }                                                                               // after execute each command
}
