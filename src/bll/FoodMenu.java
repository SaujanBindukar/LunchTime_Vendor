package bll;

import java.io.Serializable;

public class FoodMenu implements Serializable {
    private int food_id, food_price;
    private String food_name;
    private String picture;
    public static final long serialVersionUID =1L;



    public FoodMenu(int food_id, String food_name, int food_price, String picture) {
        this.food_id=food_id;
        this.food_name=food_name;
        this.food_price=food_price;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public FoodMenu() {

    }


    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getFood_price() {
        return food_price;
    }

    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }


}
