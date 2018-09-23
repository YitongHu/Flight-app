package cs.b07.p2classes.flights;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Flight object.
 * @author Calvin, Hassan, Vithusan, Yeo, Yi
 */
public class Flight implements Serializable{

  private static final long serialVersionUID = -2199564557518526425L;
  private String flightNumber;
  private String departure;
  private String arrival;
  private String airline;
  private String origin;
  private String destination;
  private double cost;
  private Set<Flight> nextFlights;
  private int numSeats;

  /**
   * Creates a Flight with the given parameters.
   * @param flightNumber the number of the Flight
   * @param departure the time and date of departure
   * @param arrival the time and date of arrival
   * @param airline the airline used for this Flight
   * @param origin of the Flight
   * @param destination of the Flight
   * @param cost for a single seat of the Flight
   */
  public Flight(String flightNumber, String departure, String arrival, String airline,
      String origin, String destination, double cost, int numSeats) {
    this.flightNumber = flightNumber;
    this.departure = departure;
    this.arrival = arrival;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.cost = cost;
    this.numSeats = numSeats;
    this.nextFlights = new HashSet<Flight>();
  }

  /**
   * Returns the Flight number.
   * @return flightNumber of the Flight
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  /**
   * Sets the Flight number to the given String.
   * @param flightNumber the number to set
   */
  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  /**
   * Returns the departure date and time of the Flight.
   * @return departure of the Flight
   */
  public String getDeparture() {
    return departure;
  }

  /**
   * Returns the departure date of the Flight.
   * @return departure date of the Flight
   */
  public String getDepartureDate() {
    return departure.split(" ")[0];
  }

  /**
   * Returns the departure time of the Flight.
   * @return departure time of the Flight
   */
  public String getDepartureTime() {
    return departure.split(" ")[1];
  }
  
  /**
   * Sets the departure date and time of the Flight.
   * @param departure to set
   */
  public void setDeparture(String departure) {
    this.departure = departure;
  }

  /**
   * Returns the arrival date and time of the Flight.
   * @return arrival of the Flight
   */
  public String getArrival() {
    return arrival;
  }

  /**
   * Returns the arrival date of the Flight.
   * @return arrival date of the Flight
   */
  public String getArrivalDate() {
    return arrival.split(" ")[0];
  }
  
  /**
   * Returns the arrival time of the Flight.
   * @return arrival time of the Flight
   */
  public String getArrivalTime() {
    return arrival.split(" ")[1];
  }
  
  /**
   * Sets the arrival date and time of the Flight.
   * @param arrival to set
   */
  public void setArrival(String arrival) {
    this.arrival = arrival;
  }

  /**
   * Returns the airline name of the Flight.
   * @return the airline of the Flight
   */
  public String getAirline() {
    return airline;
  }

  /**
   * Sets the airline name of the Flight to the given String.
   * @param airline to set
   */
  public void setAirline(String airline) {
    this.airline = airline;
  }

  /**
   * Returns the origin of the Flight.
   * @return the origin of the Flight
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Sets the given String to the origin of the Flight.
   * @param origin to set
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * Returns the destination of the Flight.
   * @return the destination of the Flight
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Sets the given String to the destination of the Flight.
   * @param destination to set
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Returns the cost of the Flight.
   * @return the cost of the Flight
   */
  public int getNumSeats() {
    return numSeats;
  }
  public void setNumSeats(int seats) {numSeats = seats;}
  public Double getCost() {
    return cost;
  }

  /**
   * Sets the given double as the cost of the Flight.
   * @param cost to set the Flight to
   */
  public void setCost(Double cost) {
    this.cost = cost;
  }
  
  /**
   * Given a flight, check if that flight's origin is the same as current
   * flight's destination.
   * 
   * @param flight the flight to compare
   * @return true if current flight's destination is the same as given flight's
   *         origin; false otherwise.
   */
  public boolean compareDestination(Flight flight) {
    return this.getDestination().equals(flight.getOrigin());
  }
  
  /**
   * Given a flight, return true if the given flight departs after the current
   * flight arrives; false otherwise.
   * @param flight the flight to compare
   * @return true if given flight departs after the current one arrives, false otherwise.
   */
  public boolean isAfter(Flight flight) {
    boolean isAfter = true;
    if (getTimeDifference(flight) < 0) {
      isAfter = false;
    }
    return isAfter;
  }
  
  /**
   * Given another flight, returns the time difference in minutes between the arrival time
   * of the current flight and the departure of the given flight.
   * @param flight the flight to compare the time difference to
   * @return time the time difference between both flights
   */
  public int getTimeDifference(Flight flight) {
    int time = 0;
    String[] arrivalDate = splitDate(getArrival()); 
    String[] arrivalTime = splitTime(getArrival());
    String[] departureDate = splitDate(flight.getDeparture());
    String[] departureTime = splitTime(flight.getDeparture());
    
    time = 60 * (Integer.parseInt(departureTime[0]) - Integer.parseInt(arrivalTime[0]));
    int minutes = Integer.parseInt(departureTime[1]) - Integer.parseInt(arrivalTime[1]);
    time += minutes;
    int days = 0;
    
    // Case for when only the day is different
    if (departureDate[0].equals(arrivalDate[0]) && departureDate[1].equals(arrivalDate[1])
        && departureDate[2].equals(arrivalDate[2]) == false) {
      // 1440 minutes in a day
      time += 1440 * (Integer.parseInt(departureDate[2]) - Integer.parseInt(arrivalDate[2]));

    // Case for when month is different
    } else if (departureDate[0].equals(arrivalDate[0]) && departureDate[1].equals(arrivalDate[1])
        == false) {
      // Set days of the month depending on the calendar month
      if (arrivalDate[1].equals("01") || arrivalDate[1].equals("03") || arrivalDate[1].equals("05")
          || arrivalDate[1].equals("07") || arrivalDate[1].equals("08")
          || arrivalDate[1].equals("10") || arrivalDate[1].equals("12")) {
        days = 31;
      } else if (arrivalDate[1].equals("04") || arrivalDate[1].equals("06")
          || arrivalDate[1].equals("09") || arrivalDate[1].equals("11")) {
        days = 30;
      } else {
        days = 28;
      }
      // find the between in days between the months
      int daysFlight = days - Integer.parseInt(arrivalDate[2]) + Integer.parseInt(departureDate[2]);
      // For simplicity reasons, use 30 as the number of days in a month
      time += 1440 * (30 * (Integer.parseInt(departureDate[1]) - Integer.parseInt(arrivalDate[1])
          - 1) + daysFlight);
      
    // Case for when the year is different
    } else if (departureDate[0].equals(arrivalDate[0]) == false) {
      days = 31 - Integer.parseInt(arrivalDate[2]) + Integer.parseInt(departureDate[2]);
      // Determine the number of months apart the flights are
      int numMonthsArrive = (Integer.parseInt(arrivalDate[0]) * 12)
          + Integer.parseInt(arrivalDate[1]);
      int numMonthsDepart = (Integer.parseInt(departureDate[0]) * 12)
          + Integer.parseInt(departureDate[1]);
      time += 1440 * (30 * (numMonthsDepart - numMonthsArrive - 1) + days);
      if (numMonthsDepart - numMonthsArrive == -1) {
        time *= -1;
      }
    }
    return time;
  }
  
  /**
   * Given a date as a String, returns the date in a list separated by day, month and year.
   * @param date the date of a flight as a String
   * @return the date of the flight in a list of Strings, separated by day, month and year
   */
  private String[] splitDate(String date) {
    String[] splitDate = date.split(" ")[0].split("-");
    return splitDate;
    
  }
  
  /**
   * Given a date as a String, returns the time of date in a list separated by hours and minutes.
   * @param date the date of the flight as a String
   * @return the time of the flight in a list of Strings, separated by hours and minutes
   */
  private String[] splitTime(String date) {
    String[] splitTime = date.split(" ")[1].split(":");
    return splitTime;
    
  }
  
  /**
   * Adds flight to this flights nextFlights if it can be taken directly after this flight.
   * @param flight the flight to add to the list
   */
  public void addToNextFlights(Flight flight) {
    if ((this.compareDestination(flight)) && (this.isAfter(flight))) {
      this.nextFlights.add(flight);
    }
  }
  
  /**
   * Returns all Flights after the current Flight.
   * @return the Set of Flights after the current Flight
   */
  public Set<Flight> getNextFlights() {
    return this.nextFlights;
  }

  /**
   * Returns String format of the Flight.
   */
  public String toString() {
    String ret = "";
    String cost = String.format("%.2f", this.getCost());
    String seats = String.valueOf(this.getNumSeats());
    ret = this.getFlightNumber() + "," + this.getDeparture() + "," + this.getArrival() + ","
        + this.getAirline() + "," + this.getOrigin() + "," + this.getDestination() + ","
        + cost + "," + seats;
    return ret;
  }
  
  /**
   * Returns String format of the Flight without the cost included.
   * @return String representation of Flight without cost.
   */
  public String toStringNoCost() {
    String ret = "";
    // append all flight information except cost
    ret = this.getFlightNumber() + "," + this.getDeparture() + "," + this.getArrival() + ","
            + this.getAirline() + "," + this.getOrigin() + "," + this.getDestination();
    return ret;
  }

  public String toStringFormatted() {
      String str = "";
      String newLine = "\n";
      // append all flight information with formatting
      str += "FlightNumber: " + this.getFlightNumber() + ", with Airline: " + this.getAirline();
      str += newLine;
      str += "From: " + this.getOrigin() + ", To: " + this.getDestination();
      str += newLine;
      str += "Departure: " + this.getDeparture() + ", Arrival: " + this.getArrival();
      return str;
  }

}
