/**
 * @author Saujan Bindukar
 * Interface for fetching the food menu, add new food menu,
 * deleting and updating the food menu and
 * counting the number of food.
 */
package com.lunchtime.dao;
import com.lunchtime.bll.FoodMenu;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;

public interface FoodDao extends Remote {
    ResultSet showMenu() throws RemoteException;
    String addMenu(FoodMenu fm) throws RemoteException;
    ResultSet getFoodByName(String foodName) throws RemoteException;
    void deleteMenu(String foodName) throws RemoteException;
    void updateMenu(String food_name,  int food_price, int foodId, String picture) throws RemoteException;
    ResultSet getTotalFood() throws  RemoteException;


}
