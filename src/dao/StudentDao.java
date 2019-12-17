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
