package cs.b07.p2classes.users;

import java.io.Serializable;

/**
 * Client class to represent a client using the application.
 * 
 * @author Calvin, Hassan, Vithusan, Yeo, Yi
 *
 */

public class User implements Serializable {
  private static final long serialVersionUID = -6071061218611173224L;
  private String firstName;
  private String lastName;
  private String email;
  private String address;
  private String creditCardNumber;
  private String expiryDate;

  /**
   * Creates an instance of the Client class.
   * 
   * @param lastName
   *          Last name of the client.
   * @param firstName
   *          First name of the client.
   * @param email
   *          Email of the specific client
   * @param address
   *          Where the client lives
   * @param creditCardNumber
   *          Credit Card Number used to book itinerary
   * @param expiryDate
   *          Date that the Credit Card expires
   */
  public User(String lastName, String firstName, String email, String address,
      String creditCardNumber, String expiryDate) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.address = address;
    this.creditCardNumber = creditCardNumber;
    this.expiryDate = expiryDate;
  }

  /**
   * Return the first name of the Client.
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name of Client to the given String.
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Return the last name of the Client.
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name of Client to the given String.
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the email.
   * 
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set the email of the client.
   * 
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the address.
   * 
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the address.
   * 
   * @param address
   *          the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Returns the Credit Card Number.
   * 
   * @return the creditCardNumber
   */
  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  /**
   * Sets the Credit Card Number.
   * 
   * @param creditCardNumber
   *          the creditCardNumber to set
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  /**
   * Returns the expiry date.
   * 
   * @return the expiryDate
   */
  public String getExpiryDate() {
    return expiryDate;
  }

  /**
   * Sets the expiry date.
   * 
   * @param expiryDate
   *          the expiryDate to set
   */
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  /**
   * Return a string containing client info.
   */
  @Override
  public String toString() {
    String out = "";
    out = lastName + "," + firstName + "," + email + "," + address + ","
      + creditCardNumber + "," + expiryDate;
    return out;
  }
}
