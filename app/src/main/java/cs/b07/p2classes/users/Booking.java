package cs.b07.p2classes.users;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import cs.b07.p2classes.flights.Flight;
import cs.b07.p2classes.flights.Itinerary;
import cs.b07.p2classes.users.User;

public class Booking {
  
  private HashMap<User, ArrayList<String>> clientbook;

  public Booking(String filePath) throws FileNotFoundException{
    clientbook =  new HashMap<User, ArrayList<String>>();
    //this.readFromCsvFile(filePath);
  }
/*
  public void readFromCsvFile(String fileName) throws FileNotFoundException {
    // open scanner class to read file
    Scanner sc = new Scanner(new FileInputStream(fileName));
    // Create array of strings and Flight variables
    String[] line;
    String email;
    // iterate through each line in file
    while (sc.hasNextLine()) {
      // split each line at commas
      line = sc.nextLine().split(",");
      Itinerary itin;
      String[] flights;
      ArrayList<Itinerary> itins;
      ArrayList<Flight> listFlights;
      for(int i = 1; i <= line.length; i++) {
        flights = line[i].split(":");
        for(int j = 1; j <= flights.length; j++) {
          listFlights.add()
        }
      }
    }
    // close the scanner
    sc.close();
  }
  */
  /**
   * Store the booking of a client into a csv file.
   * @param filePath is where we want to store the file
   * @param email is the user
   * @throws IOException when the file doesn't exist
   */
  public void writeTofile(String filePath, String email) throws IOException {
    File file = new File(filePath);
    // create a new file if the file is not in the path
    if (!file.exists()) {
      file.createNewFile();
    }
    String out = "";
    // make sure we can write on the file
    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    for (User client : getClients()) {
      if (client.getEmail() == email) {
        for (String itin : clientbook.get(client)) {
          out += itin + "/";
        }
      }
      // write the client info on the file
      bw.write(client.getEmail() + ":" + out);
    }
    bw.close();
  }
  
  /**
   * Return all clients into one set.
   * @return all clients
   */
  public Set<User> getClients() {
    return clientbook.keySet();
  }
  
  /**
   * Add Client to the hashmap.
   * @param lastName client last name
   * @param firstName client first name
   * @param email client email
   * @param address client address
   * @param creditCardNumber client credit card
   * @param expiryDate credit card expire date
   */
  public void addClient(String lastName, String firstName, String email, String address,
      String creditCardNumber, String expiryDate) {
    User client = new User(lastName,firstName,email,address,creditCardNumber,expiryDate);
    ArrayList<String> book = new ArrayList<String>();
    clientbook.put(client, book);
  }
  

  
  /**
   * Store all booking into hashmap.
   * @param email is client
   * @param book is a list of all the booking of client
   */
  public void addAllBooking(String email, ArrayList<String> book) {
    for (User theclient : getClients()) {
      if (theclient.getEmail() == email){
        clientbook.get(theclient).addAll(book);
      }
    }
  }
  
  /**
   * Return the booking of required client.
   * @param client is the person we want the bookings for
   * @return list of his/her booking
   */
  public ArrayList<String> getBookings(User client) {
    for (User theclient : getClients()) {
      if (theclient.equals(client)){
        return clientbook.get(theclient);
      }
    }
    return null;
  }
  
  /**
   * Remove the booking client don't want to book anymore.
   * @param email is the client
   * @param book is the booking we want to remove
   */
  public void removeBooking(String email, String book) {
    for (User client : getClients()) {
      if (client.getEmail() == email) {
        int i = 0;
        for ( String itin : clientbook.get(email)){
          if (itin == book) {
            clientbook.get(email).remove(i);
          }
          i += 1;
        }
        i = 0;
      }
    }
  }
  
  /**
   * remove all the booking for the client.
   * @param email is the client we want to remove booking
   */
  public void removeAllBooking(String email) {
    for (User client : getClients()) {
      if (client.getEmail() == email) {
        clientbook.get(email).clear();
      }
    }
  }
  
  /**
   * returns a string representation of the hashmap.
   */
  public String toString() {
    String info = "";
    for (User client : getClients()) {
      info += client.getEmail() + ":";
      for (String itin : getBookings(client)) {
        info += itin + "/";
      }
      info += "n";
    }
    return info;
  }
  
}
