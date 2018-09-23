package cs.b07.p2classes.users;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class to represent Authentication, that maps e-mail to password.
 *
 * @author Calvin, Hassan, Vithusan, Yeo, Yi
 *
 */
public class Authentication implements Serializable{
    private HashMap<String, ArrayList<String>> passwords = new HashMap<>();

    public  Authentication(String path) throws FileNotFoundException {
        this.storeAuthentication(path);
    }

    public  void storeAuthentication(String layoutFileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(layoutFileName));
        // while to read the whole file
        while (sc.hasNextLine()) {
            String[] currentline = sc.nextLine().split(",");
            // then store it into the hashmap
            ArrayList<String> info = new ArrayList<>();
            info.add(currentline[1]);
            info.add(currentline[2]);
            passwords.put(currentline[0], info);
        }
        sc.close();
    }

    /**
     * Returns true if the given email and password belongs to a user; false otherwise.
     *
     * @param email the email of the user to check
     * @param password the password of the user to check
     * @return ret whether or not the given email and password belongs to a user.
     */
    public String isUser(String email, String password) throws NullPointerException {
        String ret = "";
        if (passwords.keySet().contains(email)) {
            if (passwords.get(email).get(0).equals(password)) {
                ret = passwords.get(email).get(1);
            }
        }
        return ret;
    }

    /**
     * Adds a user to the HashMap given an email, password and user type.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @param userType to distinguish between Client or Admin
     */
    public void addUser(String email, String password, String userType) {
        ArrayList<String> info = new ArrayList<String>();
        info.add(password);
        info.add(userType);
        passwords.put(email, info);
    }

    /**
     * Returns the password of the given email.
     * @param email the email to return the password of
     * @return the password
     */
    public String getPassword(String email) {
        return passwords.get(email).get(0);
    }

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
        for (String email : passwords.keySet()) {
            bw.write(email + "," + passwords.get(email).get(0) + "," + passwords.get(email).get(1) + "\n");
        }
        // close file when done
        bw.close();
    }

}
