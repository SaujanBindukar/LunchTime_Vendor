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
    public  void updateStatus(int order_id) throws  RemoteException;
    ResultSet getUserOrderByDate(LocalDate initialDate, LocalDate finalDate) throws  RemoteException;
    ResultSet getUserOrderByName(String firstName) throws RemoteException;
    ResultSet getSalesDetail() throws RemoteException;

}
