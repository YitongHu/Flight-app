package cs.b07.p2classes.flights;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents an Itinerary that connects one or more Flights.
 * 
 * @author Calvin, Hassan, Vithusan, Yeo, Yi
 */
public class Itinerary implements Serializable {

  private List<Flight> flightList = new ArrayList<>();
  private double cost;
  private int time;
  private static final long serialVersionUID = 8194138194619067138L;

  /**
   * Creates the Itinerary given each Flight's information.
   * 
   * @param flights the List of Flights to add to the Itinerary
   */
  public Itinerary(List<Flight> flights) {
    this.flightList = flights;
    this.cost = 0.0;
    String departure = "";
    String arrival = "";
    // Find the departure time of the first flight, and the arrival at the last
    // flight
    for (Flight flight : flightList) {
      cost += flight.getCost();
      arrival = flight.getArrival();
    }
    departure = (flightList.get(0)).getDeparture();
    this.time = findTravelTime(departure, arrival);
  }

  /**
   * Given the departure date and arrival date, finds the total travel time in
   * minutes.
   * 
   * @param departure date and time
   * @param arrival date and time
   * @return time in minutes of the Itinerary
   */
  private int findTravelTime(String departure, String arrival) {
    int time = 0;
    // Split strings into lists of the dates and times
    String[] departureL = departure.split(" ");
    String[] arrivalL = arrival.split(" ");
    String[] departureDate = departureL[0].split("-"); 
    String[] departureTime = departureL[1].split(":"); 
    String[] arrivalDate = arrivalL[0].split("-"); 
    String[] arrivalTime = arrivalL[1].split(":");

    time = 60 * (Integer.parseInt(arrivalTime[0]) - Integer.parseInt(departureTime[0]));
    int minutes = Integer.parseInt(arrivalTime[1]) - Integer.parseInt(departureTime[1]);
    time += minutes;
    int days = 0;

    // Case for when only the day is different
    if (departureDate[0].equals(arrivalDate[0]) && departureDate[1].equals(arrivalDate[1])
        && departureDate[2].equals(arrivalDate[2]) == false) {
      // 1440 minutes in a day
      time += 1440 * (Integer.parseInt(arrivalDate[2]) - Integer.parseInt(departureDate[2]));
    // Case for when month is different
    } else if (departureDate[0].equals(arrivalDate[0]) && departureDate[1].equals(arrivalDate[1])
        == false) {
      // Set days of the month depending on the calendar month
      if (departureDate[1].equals("01") || departureDate[1].equals("03")
          || departureDate[1].equals("05") || departureDate[1].equals("07")
          || departureDate[1].equals("08") || departureDate[1].equals("10")
          || departureDate[1].equals("12")) {
        days = 31;
      } else if (departureDate[1].equals("04") || departureDate[1].equals("06")
          || departureDate[1].equals("09")
          || departureDate[1].equals("11")) {
        days = 30;
      } else {
        days = 28;
      }
      // find the between in days between the months
      int daysFlight = days - Integer.parseInt(departureDate[2]) + Integer.parseInt(arrivalDate[2]);
      // For simplicity reasons, use 30 as the number of days in a month
      time += 1440 * (30 * (Integer.parseInt(arrivalDate[1]) - Integer.parseInt(departureDate[1])
          - 1) + daysFlight);

    // Case for when year is different
    } else if (departureDate[0].equals(arrivalDate[0]) == false) {
      days = 31 - Integer.parseInt(departureDate[2]) + Integer.parseInt(arrivalDate[2]);
      // Determine the number of months apart the flights are
      int numMonthsArrive = (Integer.parseInt(arrivalDate[0]) * 12)
          + Integer.parseInt(arrivalDate[1]);
      int numMonthsDepart = (Integer.parseInt(departureDate[0]) * 12)
          + Integer.parseInt(departureDate[1]);
      time += 1440 * (30 * (numMonthsArrive - numMonthsDepart - 1) + days);
      if (numMonthsArrive - numMonthsDepart == -1) {
        time *= -1;
      }
    }
    return time;
  }

  /**
   * Returns the total cost of the Itinerary.
   * @return cost of the Itinerary
   */
  public String getCost() {
    String cost = String.format("%.2f", this.cost);
    return cost;
  }

  /**
   * Returns the total travel time of the Itinerary.
   * @return time the total travel time of the Itinerary
   */
  public int getTime() {
    return time;
  }
  
  /**
   * Returns the time in hours and minutes of the Itinerary.
   * @return time the str representation of the time
   */
  public String getTimeHours() {

    int hours = (this.time / 60);
    int minutes = (this.time % 60);

    String time = String.format("%02d:%02d", hours, minutes);
    return time;
  }
  
  /**
   * Returns the String representation of the Itinerary.
   * @return str, String representation of Itinerary
   */
  public String toString() {
    String str = "";
    // Loop through the flight list and add each Flight on its own separate line
    for (int k = 0; k < flightList.size(); k++) {
      int flightSize = flightList.size();
      if (flightSize >= 1) {
        String line1 = flightList.get(k).toStringNoCost();
        str += line1 + "\n";
        flightSize -= 1;
      } else {
        String line1 = flightList.get(k).toStringNoCost();
        str += line1 + "\n";
      }
    }
    // Add the total cost/hours of the Itinerary on separate lines
    String line2 = this.getCost();
    String line3 = this.getTimeHours();
    str += line2 + "\n";
    str += line3;
    return str;
  }

  public String toFormattedString() {
    String str = "";
    // Loop through the flight list and add each Flight on its own separate line
    for (int k = 0; k < flightList.size(); k++) {
      int flightSize = flightList.size();
      if (flightSize >= 1) {
        String line1 = flightList.get(k).toStringFormatted();
        str += line1 + "\n";
        flightSize -= 1;
      } else {
        String line1 = flightList.get(k).toStringFormatted();
        str += line1 + "\n";
      }
    }
    // Add the total cost/hours of the Itinerary on separate lines
    String line2 = "Trip Cost: " + this.getCost();
    String line3 = "Trip Duration: " + this.getTimeHours();
    str += line2 + "\n";
    str += line3;
    return str;
  }
}
  

