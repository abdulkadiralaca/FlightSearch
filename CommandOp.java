import java.text.ParseException;
import java.util.*;

public class CommandOp extends DiaGraph{

    Map<String, ArrayList<String>> airportList = new HashMap<>();

    public String listAll(String departure, String arrival, String date) throws ParseException {
        ArrayList<Flight> asd = new ArrayList<>();       // to store paths which was find in dfs
        Map<String, Boolean> isVisited = new HashMap<>();          // is visited array
        listAllArray = new ArrayList<>();       // this array is for storing all available paths was find in dfs
        fillingVisited(isVisited);              // marking all of airports as not visited
        output = "command : listAll\t"+departure+"->"+arrival+"\t"+date+"\n";   // first line of the output.txt
        for(String departureName : airportList.get(departure)){         //looking for each airport of departure city
            if(isContain(graph, departureName)){                        //checking if the current departure airport is in graph or not
                for(String arrivalName : airportList.get(arrival)){     //looking for each airport of arrival city
                    if(isContain(graph, arrivalName)){                  //checking if the current arrival airport is in graph or not
                        findAllPaths(departureName, arrivalName, departure, arrival, isVisited, asd);           // calling dfs
                    }       //I tried to find all possible start and finish airports according to given city names
                }
            }
        }
        if(listAllArray.isEmpty()){
            return output + "No suitable flight plan is found\n\n";         //if there is no suitable flight plan writing this line
        }
        return output+"\n";     //else returning this output variable which was prepared in dfs
    }

    public String listProper(String departure, String arrival, String date) throws ParseException {
        listAll(departure,arrival,date);     //calling list all method. because we are choosing proper paths from result of listAll command
        listProperArray = new ArrayList<>();
        output = "command : listProper\t"+departure+"->"+arrival+"\t"+date+"\n";
        for(int i = 0; i < listAllArray.size(); i++){
            if(listProperArray.isEmpty()){
                listProperArray.add(listAllArray.get(i));     // if listProperArray is empty I add the i th element of listAllArray
            }
            else{
                List<List<Flight>> properCopy = new ArrayList<>(listProperArray);  // since I will do some changes on properArray I used copy
                for(List<Flight> flights : properCopy){                             // of properArray for iteration
                    String[] properDurArr = getTotalDuration(flights).split(":");
                    String[] allDurArr = getTotalDuration(listAllArray.get(i)).split(":");    // calculating total duration of path
                    if(getTotalPrice(listAllArray.get(i)) < getTotalPrice(flights) &    // checking if current path is cheaper than proper one
                            ((Integer.parseInt(allDurArr[0]) < Integer.parseInt(properDurArr[0]))|  // and also checking if it is quicker
                                    ((Integer.parseInt(allDurArr[0]) == Integer.parseInt(properDurArr[0]))&
                                            (Integer.parseInt(allDurArr[1]) < Integer.parseInt(properDurArr[1]))))){
                        listProperArray.remove(flights);        // if it is both cheaper and quicker, I deleted all paths in the proper list
                        listProperArray.add(listAllArray.get(i));   // and I add the current path to properList
                    }
                    else if(getTotalPrice(listAllArray.get(i)) < getTotalPrice(flights) |       // if current path is only cheaper or only
                            ((Integer.parseInt(allDurArr[0]) < Integer.parseInt(properDurArr[0]))| // quicker than proper one, this block is
                                    ((Integer.parseInt(allDurArr[0]) == Integer.parseInt(properDurArr[0]))& // executing
                                            (Integer.parseInt(allDurArr[1]) < Integer.parseInt(properDurArr[1]))))){
                        if(!listProperArray.contains(listAllArray.get(i))){
                            listProperArray.add(listAllArray.get(i));     // adding current path to properListArray without deleting nothing
                        }
                    }
                }
            }
        }
        if(listProperArray.isEmpty()){
            return output + "No suitable flight plan is found\n\n";   // if there is no proper path returning this string
        }
        for(List<Flight> flights : listProperArray){
           output = toStringForOutput(flights);    // else returning all proper paths after add them to output variable
        }
        return output+"\n";
    }

    public String listCheapest(String departure, String arrival, String date) throws ParseException {
        listAll(departure,arrival,date); //calling list all method. because we are choosing proper paths from result of listAll command
        output = "command : listCheapest\t"+departure+"->"+arrival+"\t"+date+"\n";
        List<List<Flight>> cheapest = new ArrayList<>();
        cheapest.add(listAllArray.get(0));    // adding first element of listAllArray to have a path which can compare with other paths
        int a = 0;
        for(List<Flight> flights : listAllArray){
            if(getTotalPrice(flights) < getTotalPrice(cheapest.get(0))){  // if current path is cheaper
                cheapest.clear();                       // clearing list and adding the current cheaper path to cheapest Array
                cheapest.add(flights);
            }
            else if(getTotalPrice(flights) == getTotalPrice(cheapest.get(0))){  // to find if there is more than cheapest path which have
                if(a == 0){                                                     // the same prices
                    a++;                                        // this if block is for avoiding the adding the same path
                    continue;
                }
                else{
                    cheapest.add(flights);    // adding it cheapest array
                }
            }
        }
        if(cheapest.isEmpty()){
            return output + "No suitable flight plan is found\n";     // if there is no proper path returning this string
        }
        for(List<Flight> flights : cheapest){
            output = toStringForOutput(flights);           // else returning all cheapest paths after adding them to output variable
        }
        return output+"\n";
    }

    public String listQuickest(String departure, String arrival, String date) throws ParseException {
        listAll(departure,arrival,date);    //calling list all method. because we are choosing proper paths from result of listAll command
        output = "command : listQuickest\t"+departure+"->"+arrival+"\t"+date+"\n";
        List<List<Flight>> quickest = new ArrayList<>();
        int a = 0;
        quickest.add(listAllArray.get(0));     // adding first element of listAllArray to have a path which can compare with other paths
        for(List<Flight> flights : listAllArray){
            String[] allDur = getTotalDuration(flights).split(":");
            String[] quickestDur = getTotalDuration(quickest.get(0)).split(":");
            if((Integer.parseInt(allDur[0]) < Integer.parseInt(quickestDur[0])) |        // if current path is quicker
                    ((Integer.parseInt(allDur[0]) == Integer.parseInt(quickestDur[0]))&
                        (Integer.parseInt(allDur[1]) < Integer.parseInt(quickestDur[1])))){
                quickest.clear();                             // clearing list and adding the current quicker path to quickest Array
                quickest.add(flights);
            }
            else if((Integer.parseInt(allDur[0]) == Integer.parseInt(quickestDur[0]))&
                    (Integer.parseInt(allDur[1]) == Integer.parseInt(quickestDur[1]))){// to find if there is more than quickest path which have
                if(a == 0){                                                          // the same durations
                    a++;                                                        // this if block is for avoiding the adding the same path
                    continue;
                }
                else{
                    quickest.add(flights);                      // adding it cheapest array
                }

            }
        }
        if(quickest.isEmpty()){
            return output + "No suitable flight plan is found\n\n";            // if there is no quickest path returning this string
        }
        for(List<Flight> flights : quickest){
            output = toStringForOutput(flights);                // else returning all quickest paths after adding them to output variable
        }
        return output+"\n";
    }

    public String listCheaper(String departure, String arrival, String date, int value) throws ParseException {
        listAll(departure,arrival,date);   //calling list all method. because we are choosing proper paths from result of listAll command
        listProper(departure,arrival,date); // calling listProper method. because we are choosing cheaper paths from result of properLis command
        output = "command : listCheaper\t"+departure+"->"+arrival+"\t"+date+"\t"+value+"\n";
        List<List<Flight>> cheaper = new ArrayList<>();
        for(List<Flight> flights : listProperArray){   // checking each path in proper list
            if(getTotalPrice(flights) < value){        // if the current path is cheaper than given value
                cheaper.add(flights);                  // adding the current path to cheaper array
            }
        }
        if(cheaper.isEmpty()){
            return output + "No suitable flight plan is found\n\n";         // if there is no cheaper path returning this string
        }
        for(List<Flight> flights : cheaper){
            output = toStringForOutput(flights);        // else returning all cheaper paths after adding them to output variable
        }
        return output+"\n";

    }

    public String listQuicker(String departure, String arrival, String date, Date latestDate, String dateStr) throws ParseException {
        listAll(departure,arrival,date);    //calling list all method. because we are choosing proper paths from result of listAll command
        listProper(departure,arrival,date); // calling listProper method. because we are choosing cheaper paths from result of properLis command
        output = "command : listQuicker\t"+departure+"->"+arrival+"\t"+date+"\t"+dateStr+"\n";
        List<List<Flight>> quicker = new ArrayList<>();
        for(List<Flight> flights : listProperArray){       // checking each path in proper list
            if(flights.get(flights.size()-1).getArrivalDate().before(latestDate)){          // if the current path is quicker than given date
                quicker.add(flights);                                   // adding the current path to quicker array
            }
        }
        if(quicker.isEmpty()){
            return output + "No suitable flight plan is found\n\n";     // if there is no quicker path returning this string
        }
        for(List<Flight> flights : quicker){
            output = toStringForOutput(flights);        // else returning all quicker paths after adding them to output variable
        }
        //System.out.println(output+"\n");
        return output+"\n";
    }

    public String listExcluding(String departure, String arrival, String date, String excludingBrand) throws ParseException {
        listAll(departure,arrival,date);    //calling list all method. because we are choosing proper paths from result of listAll command
        listProper(departure,arrival,date); // calling listProper method. because we are choosing excluding special brand paths from result of properLis command
        output = "command : listExcluding\t"+departure+"->"+arrival+"\t"+date+"\t"+excludingBrand+"\n";
        List<List<Flight>> brands = new ArrayList<>();
        for(List<Flight> flights : listProperArray){     // checking each path in proper list
            boolean isIncluding = false;
            for(Flight flight : flights){       //cheacking each flight in the current path
                if(flight.getFlightId().substring(0,2).equals(excludingBrand)){     // if any flight includes the excluding brand which was given
                    isIncluding = true;             // as a parameter, I eliminate it
                }
            }
            if(!isIncluding){
                brands.add(flights);                //if does not include I added it to brands array
            }
        }
        if(brands.isEmpty()){
            return output + "No suitable flight plan is found\n\n";         // if there is no suitable path returning this string
        }
        for(List<Flight> flights : brands){
            output = toStringForOutput(flights);        // else returning all suitable paths after adding them to output variable
        }
        //System.out.println(output+"\n");
        return output+"\n";
    }

    public String listOnlyFrom(String departure, String arrival, String date, String includingBrand) throws ParseException {
        listAll(departure,arrival,date);    //calling list all method. because we are choosing proper paths from result of listAll command
        listProper(departure,arrival,date); // calling listProper method. because we are choosing special including brand paths from result of properLis command
        output = "command : listOnlyFrom\t"+departure+"->"+arrival+"\t"+date+"\t"+includingBrand+"\n";
        List<List<Flight>> brands = new ArrayList<>();
        for(List<Flight> flights : listProperArray){    // checking each path in proper list
            boolean isexcluding = false;
            for(Flight flight : flights){                       // if any flight does not includes the brand which was given
                if(!flight.getFlightId().substring(0,2).equals(includingBrand)){
                    isexcluding = true;                  // as a parameter, I eliminate it
                }
            }
            if(!isexcluding){
                brands.add(flights);        // if includes I added it to brands array
            }
        }
        if(brands.isEmpty()){
            return output + "No suitable flight plan is found\n\n";         // if there is no suitable path returning this string
        }
        for(List<Flight> flights : brands){
            output = toStringForOutput(flights);        // else returning all suitable paths after adding them to output variable
        }
        //System.out.println(output+"\n");
        return output+"\n";
    }

    public String diameterOfGraph(){
        output = "command : diameterOfGraph\n";
        return output + "Not implemented\n\n";
    }

    public String pageRankOfNodes(){
        output = "command : pageRankOfNodes\n";
        return output + "Not implemented\n\n";
    }

}
