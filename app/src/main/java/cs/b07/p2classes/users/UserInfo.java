package cs.b07.p2classes.users;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents a class containing the information of all Users.
 * @author Calvin, Hassan, Vithusan, Yeo, Yitong
 */
public class UserInfo implements Serializable{

  private HashMap<String, User> users = new HashMap<String, User>();

  /**
   * Creates a UserInfo class provided with a path.
   * @param path
   *             The String of where the file is found.
   * @throws FileNotFoundException
   *             Exception when the file is not found.
   */

  public UserInfo(String path) throws FileNotFoundException {
    this.storeUserMap(path);
  }

  /**
   * Takes in a file and split the user thats on a csv file and create a
   * client(user). Then add it to the map with the email as a key and all the
   * info as a value.
   *
   * @param layoutFileName
   *          is the file contain the clients
   * @throws FileNotFoundException
   *           when the file doesn't exist in the directory
   */
  public void storeUserMap(String layoutFileName) throws FileNotFoundException {
    Scanner sc = new Scanner(new File(layoutFileName));
    // while to read the whole file
    while (sc.hasNextLine()) {
      String[] currentline = sc.nextLine().split(",");
      // create a client for each line of the file
      User clientUser = new User(currentline[0], currentline[1],
              currentline[2], currentline[3], currentline[4], currentline[5]);
      // then store it into the hashmap
      users.put(clientUser.getEmail(), clientUser);
    }
    sc.close();
  }

  /**
   * Given a file and a client(user) store the client into into the file.
   * the information stored for the client with the given email
   *     in this format:
   *     LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
   *     (the ExpiryDate is stored in the format YYYY-MM-DD).
   *
   * @param layoutFileName
   *          is the file we want to store users
   * @param key
   *          is the user we want to store
   * @throws IOException if invalid.
   */
  public void storeUserFile(String layoutFileName, String key)
          throws IOException {
    File file = new File(layoutFileName);
    // create a new file if the file is not in the path
    if (!file.exists()) {
      file.createNewFile();
    }
    // make sure we can write on the file
    FileOutputStream f = new FileOutputStream(file);
    ObjectOutputStream s = new ObjectOutputStream(f);
    for (String user : getUsers()) {
      if (user == key) {
        // write the client info on the file
        s.writeObject(users.get(key).toString());
      }
    }
    s.close();
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
    for (User client : users.values()) {
      bw.write(client.toString() + "\n");
    }
    // close file when done
    bw.close();
  }

  /**
   * Create a set of all the client in the map which is a set of keys(emails).
   *
   * @return a set containing all the email of the clients (users) in the map
   */
  public Set<String> getUsers() {
    return users.keySet();
  }

  /**
   * Return the user we are searching from the hashmap given their email.
   * Client is of format
   *     LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
   *     (the ExpiryDate is stored in the format YYYY-MM-DD).
   * @param key
   *            is the email of wanted client(user)
   */
  public User getUser(String key) {
    if (getUsers().contains(key)) {
      return users.get(key);
    }
    return null;
  }

  /**
   * Check if the client is already in the map return true if the email is resgistered.
   * Otherwise return false
   *
   * @param key
   *          is the email of the client
   * @return true if the email of the client is recorded else return false
   */
  public boolean checkUser(String key) {
    for (String user : getUsers()) {
      if (user.equals(key)) {
        return true;
      }
    }
    return false;
  }

  public Map<String, User> getUserMap() {return this.users;}

  public void addClients(UserInfo other) {this.users.putAll(other.getUserMap());}


  /**
   * Get rid of the client(user) we want to remove from the hashmap by checking their email.
   *
   * @param client
   *          who we want to delete from the hashmap
   */
  public void removeUser(User client) {
    if (getUsers().contains(client.getEmail())) {
      users.remove(client.getEmail());
    }
  }

  /**
   * Add the client(user) we want to add to the hashmap with email as a key the whole
   * client as a value.
   *
   * @param client
   *             who we want to add to the hashmap
   */
  public void addUser(User client) {

    users.put(client.getEmail(), client);

  }

  /**
   * Returns the information(name, email, address, creditcard, expiredate) for
   * the given email. of the client
   *
   * @param client
   *          is the email of the client
   * @return his/her informations
   */
  public User getUserInfos(String client) {
    return users.get(client);
  }

  /**
   * Update the info of the client(user) we want to modify(firstname,lastname,email
   * address,creditcard and expirydate).
   *
   * @param info
   *          is the type of information we want to update
   * @param changeinfo
   *          is what we want to change the info into
   * @param key
   *          is the client(user) we want to update (email of client)
   */
  public void updateUser(String info, String changeinfo, String key) {
    // check if user exists
    if (getUsers().contains(key)) {
      // want to change firstname
      if (info == "firstname") {
        users.get(key).setFirstName(changeinfo);
        // want to change lastname
      } else if (info == "lastname") {
        users.get(key).setLastName(changeinfo);
        // want to change email
      } else if (info == "email") {
        users.get(key).setEmail(changeinfo);
        // want to change address
      } else if (info == "address") {
        users.get(key).setAddress(changeinfo);
        // want to change creditcard number
      } else if (info == "creditcard") {
        users.get(key).setCreditCardNumber(changeinfo);
        // want to change the expirydate of the card
      } else if (info == "expirydate") {
        users.get(key).setExpiryDate(changeinfo);
      }
    }
  }

  /**
   * Return all clients(users) on the map and print a new client on every line.
   */
  public String toString() {
    String str = "";
    for (User client : users.values()) {
      str += client.toString() + "\n";
    }
    return str;
  }
}
