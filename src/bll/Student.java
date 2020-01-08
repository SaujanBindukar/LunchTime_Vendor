/**
 * @author Saujan Bindukar
 * A Java class which is a fully encapsulated class.
 * It has a private data member and getter and setter methods.
 */
package bll;
import java.io.Serializable;
public class Student implements Serializable {

    // private data member
	private static final long serialVersionUID = 1L;
	private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private int balance;

    // constructor with parameters
    public Student(int id, String firstName, String lastName, String phoneNumber, int balance, String email) {
        this.id= id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.balance=balance;
        this.email=email;

    }
    // getter method for balance
    public int getBalance() {
        return balance;
    }

    // setter method for balance
    public void setBalance(int balance) {
        this.balance = balance;
    }

    // getter method for id
    public int getId() {
        return id;
    }

    // setter method for id
    public void setId(int id) {
        this.id = id;
    }

    // getter method for firstName
    public String getFirstName() {
        return firstName;
    }

    // setter method for firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // getter method for lastName
    public String getLastName() {
        return lastName;
    }

    // setter method for lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // getter method for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // setter method for phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // getter method for email
    public String getEmail() {
        return email;
    }

    // setter method for email
    public void setEmail(String email) {
        this.email = email;
    }

    // getter method for password
    public String getPassword() {
        return password;
    }

    // setter method for password
    public void setPassword(String password) {
        this.password = password;
    }
}
