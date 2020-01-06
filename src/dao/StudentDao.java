/**
 * @author Saujan Bindukar
 * This is interface of the Student which is used to update the detail of the Student,
 * fetch all the detail of the Student, search the detail of username using firstName and
 * count the total number of students and count the total number of canteen coins.
 */
package dao;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;

public interface StudentDao extends Remote {
    void updateUser(String firstName, String lastName,
                    String email, String phoneNumber,
                    int currentBalance, int addBalance, int userId) throws RemoteException;
    ResultSet getAllUser() throws RemoteException;
    ResultSet searchUser(String userName) throws  RemoteException;
    ResultSet getTotalStudent() throws RemoteException;


}
