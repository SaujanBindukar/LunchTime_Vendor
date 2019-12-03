package dao;

import bll.Student;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StudentDao extends Remote {
    void updateUser(String firstName, String lastName,
                    String email, String phoneNumber,
                    int currentBalance, int addBalance, int userId) throws RemoteException;

}
