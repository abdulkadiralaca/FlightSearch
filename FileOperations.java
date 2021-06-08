import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileOperations {

    public void readingAirportsFiles(String fileName, ArrayList<Flight> flightsArr, Map<String, ArrayList<String>> airportList) {
        try {
            //airportList = new HashMap<>();
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split("\t");     // spliting name of city and name of airports and store it in parts array
                airportList.put(parts[0], new ArrayList<>());
                for (int i = 1; i < parts.length; i++) {
                    airportList.get(parts[0]).add(parts[i]);
                    for (Flight flight : flightsArr) {
                        if (flight.getDeparture().getName().equals(parts[i])) {
                            flight.getDeparture().setCity(parts[0]);    // airport names has already added to each airport object
                        }                                       // while flightList was reading. So I just added the name of cities
                        if (flight.getArrival().getName().equals(parts[i])) {     // to created objects after reading airports.txt
                            flight.getArrival().setCity(parts[0]);
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void readingFlightsFile(String fileName, ArrayList<Flight> flightsArr) {
        try {
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split("\t");  //spliting each part of one line according to tab and storing it parts array
                String[] stations = parts[1].split("->");   // splitting airport names which was given like 'IST->FCO'
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm EEE", Locale.ENGLISH);  // date format
                flightsArr.add(new Flight(parts[0], new Airport(stations[0]), new Airport(stations[1]), format.parse(parts[2]), parts[3], Integer.parseInt(parts[4])));
            }       //creating Flight object for each line of flightList.txt
            myReader.close();
        } catch (FileNotFoundException | ParseException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void readCommandFile(String fileName, CommandOp commandOp) {
        try {
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split("\t"); //spliting each part of one line according to tab and storing it parts array
                String[] stations;
                switch (parts[0]) {       // it is executing the functions according to command name
                    case "listAll":     // calling functions with necessary parameters and writing them to output.txt
                        stations = parts[1].split("->");
                        System.out.println(commandOp.listAll(stations[0], stations[1], parts[2]));
                        break;
                    case "listProper":
                        stations = parts[1].split("->");
                        System.out.println(commandOp.listProper(stations[0], stations[1], parts[2]));
                        break;
                    case "listCheapest":
                        stations = parts[1].split("->");
                        System.out.println(commandOp.listCheapest(stations[0], stations[1], parts[2]));
                        break;
                    case "listQuickest":
                        stations = parts[1].split("->");
                        System.out.println(commandOp.listQuickest(stations[0], stations[1], parts[2]));
                        break;
                    case "listCheaper":
                        stations = parts[1].split("->");
                        System.out.println(commandOp.listCheaper(stations[0], stations[1], parts[2], Integer.parseInt(parts[3])));
                        break;
                    case "listQuicker":
                        stations = parts[1].split("->");
                        ;
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm EEE", Locale.ENGLISH);
                        System.out.println(commandOp.listQuicker(stations[0], stations[1], parts[2], format.parse(parts[3]), parts[3]));
                        break;
                    case "listExcluding":
                        stations = parts[1].split("->");
                        System.out.println(commandOp.listExcluding(stations[0], stations[1], parts[2], parts[3]));
                        break;
                    case "listOnlyFrom":
                        stations = parts[1].split("->");
                        System.out.println(commandOp.listOnlyFrom(stations[0], stations[1], parts[2], parts[3]));
                        break;
                    case "diameterOfGraph":
                        System.out.println(commandOp.diameterOfGraph());
                        break;
                    case "pageRankOfNodes":
                        System.out.println(commandOp.pageRankOfNodes());
                        break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException | ParseException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}