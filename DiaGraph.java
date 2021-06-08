import java.text.ParseException;
import java.util.*;

public class DiaGraph {

    public Map<Airport, List<Flight>> graph = new HashMap<>();     // this hashmap is totally my graph. key is an airport.
                                                                // and value is list of flights which is departuring from key airport.
    public List<List<Flight>> listAllArray;               //this array is for storing flights which was selected in listAll command
    public List<List<Flight>> listProperArray;            //this array is for storing flights which was selected in listProper command
    String output;                                // I used this variable to store outputs of each command and after ı writed this variable

    public boolean isContain(Map<Airport, List<Flight>> grp, String name){
        for(Airport airp : grp.keySet()){                  // this method checks if graph is contains the airport whose name was given as parameter
            if(airp.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    private Airport getAirByName(Map<Airport, List<Flight>> grp, String name){
        for(Airport airp : grp.keySet()){               //this method finds in the graph and returns an airport whose name was given
            if(airp.getName().equals(name)){            //it finds according to airport name
                return airp;
            }
        }
        return null;
    }

    private Airport getAirByCity(Map<Airport, List<Flight>> grp, String name){
        for(Airport airp : grp.keySet()){           //this method finds in the graph and returns an airport whose city name was given
            if(airp.getCity().equals(name)){        // it finds according to city name
                return airp;
            }
        }
        return null;
    }

    public void CreateGraph(ArrayList<Flight> flightArr){      // this method is filling hashmap according to flightArray
        for(Flight airport : flightArr){
            if(!isContain(graph,airport.getDeparture().getName())){          // creating ArrayList<Flight> for each airport in flightArray
                graph.put(airport.getDeparture(), new ArrayList<Flight>());
            }
            if(!isContain(graph, airport.getArrival().getName())){
                graph.put(airport.getArrival(), new ArrayList<Flight>());
            }
        }
        for (Flight flight : flightArr){
            graph.get(getAirByName(graph,flight.getDeparture().getName())).add(flight);     // filling this ArrayList<Flight> values
        }     //according to their departure airport                            // I mean filling adjacency list in other words
    }

    public void fillingVisited(Map<String, Boolean> isVisited){
        for(Airport port : graph.keySet()){
            isVisited.put(port.getCity(), false);     // filling each index with false in isVisited
        }
    }


    public void findAllPaths(String departureAirport, String arrivalAirport , String departure, String arrival, Map<String, Boolean> isVisited, List<Flight> localPathList) throws ParseException {
         if(departure.equals(arrival)) {              //if the function reaches the arrival city the program will finish
            output = toStringForOutput(localPathList);      // after adding the path which was stored in localPathList array
            List<Flight> flights = new ArrayList<>(localPathList);
            listAllArray.add(flights);     // adding the all paths from departure to arrival station to listAllArray to use it in next commands
            return;
        }
        isVisited.put(departure, true);         // marking the current city as visited
        for (Flight airport : graph.get(getAirByName(graph, departureAirport))) {    // looking for all edges of the current airport
            if (!isVisited.get(airport.getArrival().getCity())) {      // checking if the next airport is visited or not
                if(localPathList.isEmpty() ||          // checking the landing time of first flight is before departure time of second flight
                        localPathList.get(localPathList.size()-1).getArrivalDate().before(airport.getDepartureDate())) { // or there is no flight before the current flight
                    localPathList.add(airport);    // if it is true adding the current airport to localPathList
                    findAllPaths(airport.getArrival().getName(), arrivalAirport, airport.getArrival().getCity(), arrival, isVisited, localPathList);  // going on with arrival station of current flight
                }
                localPathList.remove(airport);     // after a path was find, function removes the airports in localPathList to store again if there is any other path
            }
        }
        isVisited.put(departure, false);      // marking the removed airports as not visited again
    }

    public double calculateDiameter(){

        return 0.0;//((1-d)+(d*(pageRank(0.85, ))));
    }

    public int getTotalPrice(List<Flight> array){
        int total_price = 0;
        for(Flight flight : array){            // this method is finding the total price of all flights in a path
            total_price += flight.getPrice();
        }
        return total_price;
    }

    public String getTotalDuration(List<Flight> array) throws ParseException {
        String durationStr;             // this method is for finding total duration time of all flights in a path
        long diff = array.get(array.size()-1).getArrivalDate().getTime()-array.get(0).getDepartureDate().getTime();
        if(diff / (60 * 60 * 1000) / 24 < 1 & diff / (60 * 60 * 1000) % 24 <10){
            durationStr = "0"+diff / (60 * 60 * 1000) % 24+":";
        }
        else{               // some concetenate operations on hours and minutes to write them as a string again
            durationStr = diff / (60 * 60 * 1000)+":";
        }
        if((diff / (60 * 1000) % 60)==0){
            durationStr += "00";
        }
        else{
            if((diff / (60 * 1000) % 60)<10){
                durationStr += "0";
            }
            durationStr += (diff / (60 * 1000) % 60);
        }
        //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return durationStr;
    }

    public String toStringForOutput(List<Flight> array) throws ParseException {
        int number = 0;
        for(Flight flight: array) {     // this method is converting a flights path which ws given as a parameter to string form
            if(++number == array.size()){
                output += flight.getFlightId() +"\t"+ flight.getDeparture().getName() +"->"+ flight.getArrival().getName();
                break;              // each line of output.txt was prepared with this method
            }                       // it stores the string form in output variable and use this variable in commandOp class to return a string
            output += flight.getFlightId() +"\t"+ flight.getDeparture().getName() +"->"+ flight.getArrival().getName()+"||";
        }
        return output +"\t"+ getTotalDuration(array) +"/"+ getTotalPrice(array)+"\n";
    }
}
