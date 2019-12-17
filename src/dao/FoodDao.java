package dao;

import bll.FoodMenu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;

public interface FoodDao extends Remote {
    ResultSet showMenu() throws RemoteException;
    void addMenu(FoodMenu fm) throws RemoteException;
    ResultSet getFoodByName(String foodName) throws RemoteException;
    void deleteMenu(String foodName) throws RemoteException;
    void updateMenu(String food_name,  int food_price, int foodId, String picture) throws RemoteException;
    ResultSet getTotalFood() throws  RemoteException;


}
