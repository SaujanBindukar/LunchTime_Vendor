/**
 * @author Saujan Bindukar
 * This is the interface of the User which is used to fetch the order of food, update the food order status,
 * filters the user order by date and name, fetch the sales detail and calculates the total amount of sales,
 * fetch the highest food sold and get the student spending the highest canteen coins students and also fetch the
 * pending order and get todays order using the correct date.
 */
package dao;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.time.LocalDate;


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
