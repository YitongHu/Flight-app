package cs.b07.p2classes.flights;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FlightData implements Serializable{

  private HashMap<String, Flight> flights;
  private static final long serialVersionUID = 7237949563582585530L;

  /**
   * Creates a map based on the filePath given, creates new file if filepath
   * does not exist.
   * 
   * @param filePath Path to the file used
   * @throws IOException If the file does not exist
   */
  public FlightData(String filePath) throws FileNotFoundException {
    this.flights = new HashMap<String, Flight>();
    this.readFromCsvFile(filePath);
  }

  /**
   * Reads from CSV file and stores all flights in a map.
   * 
   * @param fileName Name of the file to read from
   * @throws FileNotFoundException If the file does not exist
   */
  public void readFromCsvFile(String fileName) throws FileNotFoundException {
    // open scanner class to read file
    Scanner sc = new Scanner(new FileInputStream(fileName));
    // Create array of strings and Flight variables
    String[] line;
    Flight flight;
    // iterate through each line in file
    while (sc.hasNextLine()) {
      // split each line at commas
      line = sc.nextLine().split(",");
      // create flight using data stored at each split point in file
      flight = new Flight(line[0], line[1], line[2], line[3], line[4], line[5],
          Double.parseDouble(line[6]), Integer.parseInt(line[7]));
      // map flight to a hashmap, flight number is the Key
      flights.put(flight.getFlightNumber(), flight);
    }
    // close the scanner
    sc.close();
  }

  /**
   * Adds flight to the FlightData.
   * 
   * @param flight
   *          the flight to be added to the hashmap
   */
  public void addFlight(Flight flight) {
    flights.put(flight.getFlightNumber(), flight);
  }

  /**
   * Return the map which contains all the flights.
   * 
   * @return flights which contains all flights
   */
  public Map<String, Flight> getFlights() {
    return this.flights;
  }

  /**
   * Given a FlightData object, adds all flights from there into this.
   * FlightData's flights
   * 
   * @param other
   *          FlightData
   */
  public void addFlights(FlightData other) {
    this.flights.putAll(other.getFlights());
  }

  /**
   * Returns a collection of all the flights.
   */
  public Collection<Flight> allFlights() {
    return flights.values();
  }

  /**
   * Returns the strings of all flights in the following format.
   * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price
   * (the dates are in the format YYYY-MM-DD; the price has exactly two decimal
   * places)
   * @param date
   *          when the flight leaves
   * @param origin
   *          where the flight is leaving
   * @param destination
   *          is where the flight reaches
   * @return string with all the wanted flights
   */
  public String wantedFlights(String date, String origin, String destination) {
    // create empty string to append flights to
    String out = "";
      // loop through flights hashmap
    for (Flight flight : allFlights()) {
      // if flight departure date equals and origin equals, and destination equals
      // flight is wanted
      if ((flight.getDepartureDate().equals(date)) && (flight.getOrigin().equals(origin))
          && (flight.getDestination().equals(destination))) {
        // add flight to string
        out += flight + "\n";
      }
    }
    return out;
  }

  /**
   * Returns flight based on flight number given.
   * 
   * @param flightNumber Number of the flight to returned
   * @return Flight corresponding to the number
   */
  public Flight getFlight(String flightNumber) {
    return this.flights.get(flightNumber);
  }

  /**
   * Adds all the nextFlights for each flight in FlightData.
   */
  public void addNextFlights() {
    // loop through each flight
    for (Flight flight : this.getFlights().values()) {
      // loop through each flight for each flight
      for (Flight nextFlight : this.getFlights().values()) {
        flight.addToNextFlights(nextFlight);
      }
    }
  }
  public ArrayList<String> getListFlights(String origin, String destination, String date) {
    ArrayList<String> returnList = new ArrayList<String>();
    for (Flight flight: this.getFlights().values()) {
      if(flight.getOrigin().equals(origin)
              && flight.getDestination().equals(destination)
              && flight.getDepartureDate().equals(date)) {
        returnList.add(flight.toString());
      }
    }
    return returnList;
  }
  
  /**
   * Save the flights back to a given file.
   */
  public void saveToFile(String filePath) throws IOException {
    // open file in file path
    File file = new File(filePath);
    // if file doesn't exist, create a new one
    if (!file.exists()) {
      file.createNewFile();
    }
    // write flight to each line in the file
    FileWriter fw = new FileWriter(file.getAbsolutePath());
    BufferedWriter bw = new BufferedWriter(fw);
    for (Flight flight : flights.values()) {
      bw.write(flight.toString() + "\n");
    }
    // close file when done
    bw.close();
  }

  /**
   * Returns whether there is a less than a 6 hour stopover time.
   * @param flight1 Flight assumed to be landing
   * @param flight2 Flight assumed to be occuring after flight 1
   * @return True if there is less than a 6 hour stopover, False otherwise.
   */
  public boolean validDiff(Flight flight1, Flight flight2) {
    if (flight1.getTimeDifference(flight2) < 360 && flight1.getTimeDifference(flight2) >= 30) {
      return true;
    }
    return false;
  }


  /**
   * Returns list of lists of flights to be turned into itineraries.
   * 
   * @param date Date of the first flight
   * @param origin Where the first flight takes place
   * @param destination Where the itinerary should end
   * @return List of lists of flights to be turned into itineraries.
   */

  public List<List<Flight>> genItinerary(String date, String origin, String destination) {

    // empty list, this is the list we want to return containing valid flights
    List<List<Flight>> wantedItineraries = new ArrayList<List<Flight>>();

    // generate valid first flights
    List<List<Flight>> itineraries = firstFlights(date, origin);

    // if there are no first flights
    if (itineraries.size() == 0) {
      return wantedItineraries;

    } else {
      // constantly loop while the itineraries aren't empty
      while (itineraries.size() != 0) {

        // grab the first list and last flight in the list
        List<Flight> flightList = itineraries.get(0);
        Flight lastFlight = flightList.get(flightList.size() - 1);

        // first check if the destination of the last flight is wanted
        // destination
        if (lastFlight.getDestination().equals(destination)) {

          // add to wanted if the destination fits, and remove it from the
          // itineraries list
          wantedItineraries.add(flightList);
          itineraries.remove(0);

        } else {

          // generate valid flights
          List<Flight> validFlights = validFlights(lastFlight);

          // if there are no more valid flights to be added, then remove the
          // checked list
          if (validFlights.size() == 0) {
            itineraries.remove(0);

          } else {

            // loop through all elements in valid flights, and make a new list
            for (int idx = 0; idx < validFlights.size(); idx++) {

              // temp list, add all of the original flights, then add one of the
              // valid flights
              List<Flight> tempList = new ArrayList<Flight>();
              tempList.addAll(flightList);
              
              // check if the flight visits a location twice, if no then add to itineraries
              if (repeat(flightList, validFlights.get(idx).getDestination()) == false){
                tempList.add(validFlights.get(idx));
                // add to the end of the flight list
                itineraries.add(tempList);
                
              }
              
            }
            itineraries.remove(0);
            
          }
        } 
      }
    }
    return wantedItineraries;
  }


  /**
   * Generates a list of itineraries containing only the first flight.
   * @param date Date of the takeoff
   * @param origin Where the flight will begin from.
   * @return List of itineraries containing only the first flight
   */

  public List<List<Flight>> firstFlights(String date, String origin) {
    List<List<Flight>> itineraries = new ArrayList<List<Flight>>();

    // loop through all the flights in the Map (
    for (Flight flight : flights.values()) {

      // check if the origin and the date are equal
      if (flight.getOrigin().equals(origin) && flight.getDepartureDate().equals(date)) {
        // add single flights to flight list, and itinerary list
        List<Flight> singleFlight = new ArrayList<Flight>();
        singleFlight.add(flight);
        itineraries.add(singleFlight);
      }
    }
    return itineraries;
  }

  /**
   * Returns a list of valid Flights when given a flight (i.e within 6 hours) and the locations
   * matchup.
   * @param inFlight the given input Flight 
   * @return returnFlight the list of valid Flights
   */

  public List<Flight> validFlights(Flight inFlight) {
    List<Flight> returnFlight = new ArrayList<Flight>();
    // loop through hashmap of flights
    for (Flight flight : flights.values()) {
      // if current flight shares destination as transfer
      if (validDiff(inFlight, flight) && inFlight.compareDestination(flight)) {
        // add flight to returnFlight list
        returnFlight.add(flight);
      }
    }
    return returnFlight;
  }

  /**
   * Returns string representation of all flight data.
   * @return str string representation of flight data.
   */
  public String toString() {
    String str = "";
    // loop through map and add flights to string
    for (Flight flight : flights.values()) {
      str += flight.toString() + "\n";
    }
    return str;
  }
  
  /**
   * Returns a string representation of all the itineraries,
   * Format is as follows;
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places),
   *     followed by total duration (on its own line, in format HH:MM).
   * @param date the date of departure
   * @param origin the starting point of the Itinerary
   * @param destination of the Itinerary
   * @return str, string representation of the all potential Itineraries
   */
  public String itinStrings(String date, String origin, String destination) {
    List<List<Flight>> itineraries = genItinerary(date, origin, destination);
    String str = "";
    
    // loop through lists of list of flights
    for (int i = 0; i < itineraries.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itineraries.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      // convert each itinerary to a string and append
      str += itin.toString() + "\n";
    }
    return str;
  }
  
  /**
   * Returns a list of Itinerary
   * @param itineraries is a list of list of flight that makes an itin
   */
  public List<Itinerary> listItineraries(List<List<Flight>> itineraries){

    List<Itinerary> wantedItineraries = new ArrayList<Itinerary>();
    for (int i = 0; i < itineraries.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itineraries.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      wantedItineraries.add(itin);
    }

    return wantedItineraries;
  }

  /**
   *  Put all Itineraries into a list of strings
   * @param date the date of departure
   * @param origin the starting point of the Itinerary
   * @param destination of the Itinerary
   * @return list of all Itineraries into as strings
   */
  public List<String> itinListStrings(String date, String origin, String destination) {
    List<List<Flight>> itineraries = genItinerary(date, origin, destination);
    List<String> listStr = new ArrayList<String>();
    // loop through lists of list of flights
    for (int i = 0; i < itineraries.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itineraries.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      // convert each itinerary to a string and append
      listStr.add(itin.toString());
    }
    return listStr;
  }
  
  /**
   * Returns a string representation of all the itineraries sorted by non decreasing cost,
   * Format is as follows;
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places),
   *     followed by total duration (on its own line, in format HH:MM).
   * @param date the date of departure
   * @param origin the starting point of the Itinerary
   * @param destination of the Itinerary
   * @return str, string representation of the all potential Itineraries
   */
  public String itinStringsCost(String date, String origin, String destination) {
    List<List<Flight>> itineraries = genItinerary(date, origin, destination);
    List<List<Flight>> itinerariesCost = new ArrayList<List<Flight>>();
    // loop through list of lists of flights, remove from itineraries and add to
    // a new one after each iteration
    while (itineraries.size() != 0) {
      int smallest = 0;
      Itinerary itin = new Itinerary(itineraries.get(smallest));
      boolean isChanged = false;
      // find the index of the Itinerary with the least priced
      for (int i = 1; i < itineraries.size(); i++) {
        if (isChanged) {
          itin = new Itinerary(itineraries.get(smallest));
          isChanged = false;
        }
        Itinerary itin2 = new Itinerary(itineraries.get(i));
        if (Double.parseDouble(itin2.getCost()) < Double.parseDouble(itin.getCost())) {
          smallest = i;
          isChanged = true;
        }
      }
      itinerariesCost.add(itineraries.get(smallest));
      itineraries.remove(smallest);
    }
    String str = "";
    // loop through lists of list of flights
    for (int i = 0; i < itinerariesCost.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itinerariesCost.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      // convert each itinerary to a string and append
      str += itin.toString() + "\n";
    }
    return str;
  }

  /**
   * Returns a string representation of all the itineraries sorted by increasing travel time,
   * Format is as follows;
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places),
   *     followed by total duration (on its own line, in format HH:MM).
   * @param date the date of departure
   * @param origin the starting point of the Itinerary
   * @param destination of the Itinerary
   * @return str, string representation of the all potential Itineraries
   */
  public String itinStringsTime(String date, String origin, String destination) {
    List<List<Flight>> itineraries = genItinerary(date, origin, destination);
    List<List<Flight>> itinerariesTime = new ArrayList<List<Flight>>();
    // loop through list of lists of flights, remove from itineraries and add to
    // a new one after each iteration
    while (itineraries.size() != 0) {
      int smallest = 0;
      Itinerary itin = new Itinerary(itineraries.get(smallest));
      boolean isChanged = false;
      // find the index of the Itinerary with the least time
      for (int i = 1; i < itineraries.size(); i++) {
        if (isChanged) {
          itin = new Itinerary(itineraries.get(smallest));
          isChanged = false;
        }
        Itinerary itin2 = new Itinerary(itineraries.get(i));
        if (itin2.getTime() < itin.getTime()) {
          smallest = i;
          isChanged = true;
        }
      }
      itinerariesTime.add(itineraries.get(smallest));
      itineraries.remove(smallest);
    }
    String str = "";
    // loop through lists of list of flights
    for (int i = 0; i < itinerariesTime.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itinerariesTime.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      // convert each itinerary to a string and append
      str += itin.toString() + "\n";
    }
    return str;
  }
  
  /**
   * Returns a list representation of all the itineraries sorted by increasing travel time,
   * Format is as follows;
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places),
   *     followed by total duration (on its own line, in format HH:MM).
   * @param date the date of departure
   * @param origin the starting point of the Itinerary
   * @param destination of the Itinerary
   * @return list,list of string representation of the all potential Itineraries
   */
  public ArrayList<String> itinListTime(String date, String origin, String destination) {
    List<List<Flight>> itineraries = genItinerary(date, origin, destination);
    List<List<Flight>> itinerariesTime = new ArrayList<List<Flight>>();
    // loop through list of lists of flights, remove from itineraries and add to
    // a new one after each iteration
    while (itineraries.size() != 0) {
      int smallest = 0;
      Itinerary itin = new Itinerary(itineraries.get(smallest));
      boolean isChanged = false;
      // find the index of the Itinerary with the least time
      for (int i = 1; i < itineraries.size(); i++) {
        if (isChanged) {
          itin = new Itinerary(itineraries.get(smallest));
          isChanged = false;
        }
        Itinerary itin2 = new Itinerary(itineraries.get(i));
        if (itin2.getTime() < itin.getTime()) {
          smallest = i;
          isChanged = true;
        }
      }
      itinerariesTime.add(itineraries.get(smallest));
      itineraries.remove(smallest);
    }
    ArrayList<String> itinList = new ArrayList<String>();
    // loop through lists of list of flights
    for (int i = 0; i < itinerariesTime.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itinerariesTime.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      // convert each itinerary to a string and append
      itinList.add(itin.toFormattedString() + "\n");
    }
    return itinList;
  }
  
  /**
   * Returns a list representation of all the itineraries sorted by non decreasing cost,
   * Format is as follows;
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places),
   *     followed by total duration (on its own line, in format HH:MM).
   * @param date the date of departure
   * @param origin the starting point of the Itinerary
   * @param destination of the Itinerary
   * @return list, list of string representation of the all potential Itineraries
   */
  public ArrayList<String> itinListCost(String date, String origin, String destination) {
    List<List<Flight>> itineraries = genItinerary(date, origin, destination);
    List<List<Flight>> itinerariesCost = new ArrayList<List<Flight>>();
    // loop through list of lists of flights, remove from itineraries and add to
    // a new one after each iteration
    while (itineraries.size() != 0) {
      int smallest = 0;
      Itinerary itin = new Itinerary(itineraries.get(smallest));
      boolean isChanged = false;
      // find the index of the Itinerary with the least priced
      for (int i = 1; i < itineraries.size(); i++) {
        if (isChanged) {
          itin = new Itinerary(itineraries.get(smallest));
          isChanged = false;
        }
        Itinerary itin2 = new Itinerary(itineraries.get(i));
        if (Double.parseDouble(itin2.getCost()) < Double.parseDouble(itin.getCost())) {
          smallest = i;
          isChanged = true;
        }
      }
      itinerariesCost.add(itineraries.get(smallest));
      itineraries.remove(smallest);
    }
    ArrayList<String> itinList = new ArrayList<String>();
    // loop through lists of list of flights
    for (int i = 0; i < itinerariesCost.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itinerariesCost.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      // convert each itinerary to a string and append
      itinList.add(itin.toFormattedString() + "\n");
    }
    return itinList;
  }
  
  /**
   * Returns a list representation of all the itineraries sorted by increasing travel time,
   * Format is as follows;
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places),
   *     followed by total duration (on its own line, in format HH:MM).
   * @param date the date of departure
   * @param origin the starting point of the Itinerary
   * @param destination of the Itinerary
   * @return List, list of itin representation of the all potential Itineraries
   */
  public ArrayList<Itinerary> itineraryListTime(String date, String origin, String destination) {
    List<List<Flight>> itineraries = genItinerary(date, origin, destination);
    List<List<Flight>> itinerariesTime = new ArrayList<List<Flight>>();
    // loop through list of lists of flights, remove from itineraries and add to
    // a new one after each iteration
    while (itineraries.size() != 0) {
      int smallest = 0;
      Itinerary itin = new Itinerary(itineraries.get(smallest));
      boolean isChanged = false;
      // find the index of the Itinerary with the least time
      for (int i = 1; i < itineraries.size(); i++) {
        if (isChanged) {
          itin = new Itinerary(itineraries.get(smallest));
          isChanged = false;
        }
        Itinerary itin2 = new Itinerary(itineraries.get(i));
        if (itin2.getTime() < itin.getTime()) {
          smallest = i;
          isChanged = true;
        }
      }
      itinerariesTime.add(itineraries.get(smallest));
      itineraries.remove(smallest);
    }
    ArrayList<Itinerary> itinList = new ArrayList<Itinerary>();
    // loop through lists of list of flights
    for (int i = 0; i < itinerariesTime.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itinerariesTime.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      // convert each itinerary to a string and append
      itinList.add(itin);
    }
    return itinList;
  }
  
  /**
   * Returns a list representation of all the itineraries sorted by non decreasing cost,
   * Format is as follows;
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *     followed by total price (on its own line, exactly two decimal places),
   *     followed by total duration (on its own line, in format HH:MM).
   * @param date the date of departure
   * @param origin the starting point of the Itinerary
   * @param destination of the Itinerary
   * @return list, list of itin representation of the all potential Itineraries
   */
  public ArrayList<Itinerary> itineraryListCost(String date, String origin, String destination) {
    List<List<Flight>> itineraries = genItinerary(date, origin, destination);
    List<List<Flight>> itinerariesCost = new ArrayList<List<Flight>>();
    // loop through list of lists of flights, remove from itineraries and add to
    // a new one after each iteration
    while (itineraries.size() != 0) {
      int smallest = 0;
      Itinerary itin = new Itinerary(itineraries.get(smallest));
      boolean isChanged = false;
      // find the index of the Itinerary with the least priced
      for (int i = 1; i < itineraries.size(); i++) {
        if (isChanged) {
          itin = new Itinerary(itineraries.get(smallest));
          isChanged = false;
        }
        Itinerary itin2 = new Itinerary(itineraries.get(i));
        if (Double.parseDouble(itin2.getCost()) < Double.parseDouble(itin.getCost())) {
          smallest = i;
          isChanged = true;
        }
      }
      itinerariesCost.add(itineraries.get(smallest));
      itineraries.remove(smallest);
    }
    ArrayList<Itinerary> itinList = new ArrayList<Itinerary>();
    // loop through lists of list of flights
    for (int i = 0; i < itinerariesCost.size(); i++) {
      // for each nested list create an itinerary
      List<Flight> innerFlights = itinerariesCost.get(i);
      Itinerary itin = new Itinerary(innerFlights);
      // convert each itinerary to a string and append
      itinList.add(itin);
    }
    return itinList;
  }
  
  /**
   * Returns whether or not the destination of a flight has been visited before.
   * @param flights List containing flights in the same itinerary
   * @param destination The destination of the flight to check
   * @return True if the flight has been visited before, false if it hasn't.
   */
  
  public boolean repeat(List<Flight> flights, String destination){
    for (Flight flight: flights){
      if (flight.getOrigin().equals(destination)){
        return true;
      }
    }
    return false;
  }
  
  /**
   * Returns a better display for itineraries with more information indicators
   * @param itinList is a string representation of the itinerary
   */
  public String itinFormatter(String itinList) {

      // Create blank string to append all subsequent strings to
      String out = "";
      String newLine = "\n";
      // split the string at every new line
      String[] lines = itinList.split("\\r?\\n");
      String cost = lines[-2];
      String time = lines[-1];
      // loop through all items in the split string list, except last 2
      for (int i = 0; i + 2 < lines.length; i++) {
          // split each item by commas
          String[] splitItin = lines[i].split(",");
          // loop through and apply the necessary formatting
          for (int k = 0; k < splitItin.length; k++) {
              out += ("Flight number: " + splitItin[0] + " Airline: " + splitItin[3]);
              out += newLine;
              out += "From: " + splitItin[4] + " To: " + splitItin[5];
              out += newLine;
              out += "Departure: " + splitItin[1] + " Arrival: " + splitItin[2];
              out += newLine;
          }
      }
      // append the cost and time at the end of the formatted itinerary
      out += newLine;
      out += "Cost:" + cost;
      out += newLine;
      out += "Departure time";
      out += newLine;
      return out;
  }
}