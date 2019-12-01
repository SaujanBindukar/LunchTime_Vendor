package dao;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserOrderDao extends Remote {
    ResultSet getUserOrder() throws RemoteException, SQLException;
}
