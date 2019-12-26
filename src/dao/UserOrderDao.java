package dao;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public interface UserOrderDao extends Remote{
    ResultSet getUserOrder() throws RemoteException;
    void updateStatus(int order_id, String status) throws  RemoteException;
    ResultSet getUserOrderByDate(LocalDate initialDate, LocalDate finalDate) throws  RemoteException;
    ResultSet getUserOrderByName(String firstName) throws RemoteException;
    ResultSet getSalesDetail() throws RemoteException;
    ResultSet getSalesDetailByDate(LocalDate initialDate, LocalDate finalDate) throws  RemoteException;
    ResultSet getTotalSales() throws  RemoteException;
    ResultSet getFoodPreference() throws  RemoteException;
    ResultSet getFoodPreferenceByDate(LocalDate initialDate, LocalDate finalDate) throws RemoteException;
    ResultSet getTopUser() throws RemoteException;
    ResultSet getTopUserByDate(LocalDate initialDate, LocalDate finalDate) throws  RemoteException;
    ResultSet getPendingOrder() throws RemoteException;
    ResultSet getTodaysOrder() throws  RemoteException;



}
