package dao;

import bll.Student;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;

public interface VendorDao extends Remote , Serializable {
    Boolean checkVendor(String vendor_email, String password) throws RemoteException;
}
