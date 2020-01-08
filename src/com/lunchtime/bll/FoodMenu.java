/**
 * @author Saujan Bindukar
 * A Java class which is a fully encapsulated class.
 * It has a private data member and getter and setter methods.
 */
package com.lunchtime.bll;
import java.io.Serializable;

public class FoodMenu implements Serializable {

    //private data member

    private int food_id, food_price;
    private String food_name;
    private String picture;
    public static final long serialVersionUID =1L;
    // Empty constructor
    public FoodMenu() {

    }
    // Constructor with parameters
    public FoodMenu(int food_id, String food_name, int food_price, String picture) {
        this.food_id=food_id;
        this.food_name=food_name;
        this.food_price=food_price;
        this.picture = picture;
    }

    //getter method for picture
    public String getPicture() {
        return picture;
    }

    // setter method for picture
    public void setPicture(String picture) {
        this.picture = picture;
    }

    //getter method for food_id
    public int getFood_id() {
        return food_id;
    }

    // setter method for food_id
    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    //getter method for food_price
    public int getFood_price() {
        return food_price;
    }

    // setter method for food_price
    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    //getter method for food_name
    public String getFood_name() {
        return food_name;
    }

    // setter method for food_name
    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }


}
