/**
 * @author Saujan Bindukar
 * This is interface of the Vendor which is used to check the vandor, fetch all the information of the vendor,
 * update the profile of vendor using the vendor id.
 */

package dao;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;

public interface VendorDao extends Remote{
    Boolean checkVendor(String vendor_email, String password) throws RemoteException;
    ResultSet getVendorInfo(String email) throws RemoteException;
    void updateVendorProfile(int vendor_id, String vendor_name, String vendor_email, String vendor_number, String picture) throws RemoteException;
    ResultSet getInfo(int vendor_id) throws RemoteException;
    ResultSet getAllVendorInfo() throws  RemoteException;


}
